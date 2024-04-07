using WebStore.Data.Entities.Identity;

namespace WebStore.Interfaces
{
    public interface IJwtTokenService
    {
        Task<string> CreateToken(UserEntity user);
    }
}
