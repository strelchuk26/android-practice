using Microsoft.AspNetCore.Mvc;
using WebStore.Data;
using WebStore.Data.Entities;
using WebStore.Models.Users;
using System.Linq;
using Microsoft.AspNetCore.Identity;
using WebStore.Data.Entities.Identity;
using WebStore.Interfaces;
using System;
using WebStore.Services;
using System.IO;
using Microsoft.AspNetCore.Http;

namespace WebStore.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserManager<UserEntity> _userManager;
        private readonly IJwtTokenService _jwtTokenService;

        public UserController(UserManager<UserEntity> userManager, IJwtTokenService jwtTokenService)
        {
            _userManager = userManager;
            _jwtTokenService = jwtTokenService;
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] UserLoginViewModel model)
        {
            var user = await _userManager.FindByEmailAsync(model.Email);
            if (user == null)
            {
                return BadRequest();
            }
            var isAuth = await _userManager.CheckPasswordAsync(user, model.Password);
            if (!isAuth)
            {
                return BadRequest();
            }
            var token = await _jwtTokenService.CreateToken(user);
            return Ok(new { token });
        }

        // Реєстрація із зображенням
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromForm] UserRegisterViewModel model)
        {
            if (model == null || model.Image == null)
                return BadRequest("User data or image is missing.");

            // Перевірити чи файл є зображенням
            if (!model.Image.ContentType.StartsWith("image/"))
                return BadRequest("Uploaded file is not an image.");

            var user = new UserEntity
            {
                UserName = model.Username,
                Email = model.Email
            };

            // Конвертація зображення до масиву байтів
            byte[] imageData;
            using (var memoryStream = new MemoryStream())
            {
                await model.Image.CopyToAsync(memoryStream);
                imageData = memoryStream.ToArray();
            }

            user.ImageData = imageData;

            var result = await _userManager.CreateAsync(user, model.Password);
            if (!result.Succeeded)
                return BadRequest(result.Errors);

            var token = await _jwtTokenService.CreateToken(user);
            return Ok(new { token });
        }
    }
}

