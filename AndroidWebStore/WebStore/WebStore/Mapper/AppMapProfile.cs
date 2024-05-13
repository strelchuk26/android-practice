using AutoMapper;
using WebStore.Data.Entities;
using WebStore.Models.Categories;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace WebStore.Mapper
{
    public class AppMapProfile : Profile
    {
        public AppMapProfile()
        {
            CreateMap<CategoryEntity, CategoryItemViewModel>();
        }
    }
}
