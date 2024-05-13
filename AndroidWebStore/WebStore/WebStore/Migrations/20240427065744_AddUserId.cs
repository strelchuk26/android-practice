using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace WebStore.Migrations
{
    /// <inheritdoc />
    public partial class AddUserId : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Image",
                table: "tblCategories",
                type: "TEXT",
                nullable: true);

            migrationBuilder.AddColumn<long>(
                name: "UserId",
                table: "tblCategories",
                type: "INTEGER",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblCategories_UserId",
                table: "tblCategories",
                column: "UserId");

            migrationBuilder.AddForeignKey(
                name: "FK_tblCategories_AspNetUsers_UserId",
                table: "tblCategories",
                column: "UserId",
                principalTable: "AspNetUsers",
                principalColumn: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_tblCategories_AspNetUsers_UserId",
                table: "tblCategories");

            migrationBuilder.DropIndex(
                name: "IX_tblCategories_UserId",
                table: "tblCategories");

            migrationBuilder.DropColumn(
                name: "Image",
                table: "tblCategories");

            migrationBuilder.DropColumn(
                name: "UserId",
                table: "tblCategories");
        }
    }
}
