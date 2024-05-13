using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Webp;
using SixLabors.ImageSharp.Processing;
using WebStore.Data;
using WebStore.Data.Entities;
using WebStore.Data.Entities.Identity;
using WebStore.Models.Categories;

namespace WebStore.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class CategoriesController : ControllerBase
    {
        private readonly MyAppContext _appContext;

        public CategoriesController(MyAppContext appContext, UserManager<UserEntity> userManager,
        IMapper mapper)
        {
            _appContext = appContext;
        }

        [HttpGet]
        public IActionResult Get()
        {
            var list = _appContext.Categories.ToList();
            return Ok(list);
        }

        // Створення категорії з фото
        [HttpPost]
        public async Task<IActionResult> Create([FromForm] CategoryCreateViewModel model)
        {
            var category = new CategoryEntity
            {
                Name = model.Name,
                Description = model.Description
            };

            if (model.Image != null)
            {
                using MemoryStream ms = new MemoryStream();
                await model.Image.CopyToAsync(ms);

                using Image image = Image.Load(ms.ToArray());

                image.Mutate(x =>
                {
                    x.Resize(new ResizeOptions
                    {
                        Size = new Size(1920),
                        Mode = ResizeMode.Max
                    });
                });
                string imageName = Path.GetRandomFileName() + ".webp";
                string dirSaveImage = Path.Combine(Directory.GetCurrentDirectory(), "images", imageName);

                using var stream = System.IO.File.Create(dirSaveImage);
                await image.SaveAsync(stream, new WebpEncoder());
                category.Image = imageName;
            }

            _appContext.Categories.Add(category);
            _appContext.SaveChanges();
            return Ok(category);
        }

        // Редагування категорії з фото
        [HttpPut]
        public async Task<IActionResult> Edit([FromBody] CategoryEditViewModel model)
        {
            var category = _appContext.Categories.SingleOrDefault(x => x.Id == model.Id);
            if (category == null)
            {
                return NotFound();
            }
            if (model.Image != null)
            {
                string? imgDel = category.Image;
                if (imgDel != null)
                {
                    string imgDelPath = Path.Combine(Directory.GetCurrentDirectory(), "images", imgDel);
                    if (System.IO.File.Exists(imgDelPath))
                    {
                        System.IO.File.Delete(imgDelPath);
                    }
                }
                using Image image = Image.Load(model.Image.OpenReadStream());
                image.Mutate(x =>
                {
                    x.Resize(new ResizeOptions
                    {
                        Size = new Size(1200),
                        Mode = ResizeMode.Max
                    });
                });
                string imageName = Path.GetRandomFileName() + ".webp";
                string dirSaveImage = Path.Combine(Directory.GetCurrentDirectory(), "images", imageName);

                await image.SaveAsync(dirSaveImage, new WebpEncoder());
                category.Image = imageName;
            }
            category.Description = model.Description;
            category.Name = model.Name;
            _appContext.SaveChanges();
            return Ok(category);
        }

        // Видалення категорії з фото
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var category = _appContext.Categories.SingleOrDefault(x => x.Id == id);
            if (category == null)
            {
                return NotFound();
            }
            string? imgDel = category.Image;
            if (imgDel != null)
            {
                string imgDelPath = Path.Combine(Directory.GetCurrentDirectory(), "images", imgDel);
                if (System.IO.File.Exists(imgDelPath))
                {
                    System.IO.File.Delete(imgDelPath);
                }
            }
            _appContext.Categories.Remove(category);
            _appContext.SaveChanges();
            return Ok();
        }
    }
}