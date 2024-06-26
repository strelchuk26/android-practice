﻿namespace WebStore.Models.Users
{
    public class UserLoginViewModel
    {
        /// <summary>
        /// Пошта користувача
        /// </summary>
        /// <example>admin@gmail.com</example>
        public string Email { get; set; }
        /// <summary>
        /// Пароль користувача
        /// </summary>
        /// <example>123456</example>
        public string Password { get; set; }
    }
}
