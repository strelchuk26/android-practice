using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;

namespace WebStore.Data.Entities.Identity
{
    public class UserEntity : IdentityUser<long>
    {
        [StringLength(100)]
        public string FirstName { get; set; }
        [StringLength(100)]
        public string LastName { get; set; }
        [StringLength(100)]
        public byte[] ImageData { get; set; }
        public virtual ICollection<UserRoleEntity> UserRoles { get; set; }
        public virtual ICollection<CategoryEntity> Categories { get; set; }
    }
}
