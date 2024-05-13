namespace WebStore.Models.Users
{
    public class UserRegisterViewModel
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }

        // Додала зображення
        public IFormFile Image { get; set; }
    }
}

