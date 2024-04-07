using Microsoft.AspNetCore.Identity;

namespace WebStore.Data.Entities.Identity
{
    public class RoleEntity : IdentityRole<long>
    {
        public virtual ICollection<UserRoleEntity> UserRoles { get; set; }
    }
}
