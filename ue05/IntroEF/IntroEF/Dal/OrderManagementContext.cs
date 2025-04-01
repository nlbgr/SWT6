

using IntroEF.Domain;
using IntroEF.Utils;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Internal;
using Microsoft.Extensions.Logging;

namespace IntroEF.Dal;

public class OrderManagementContext : DbContext
{
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer(ConfigurationUtil.GetConnectionString("OrderDbConnection"))
            .EnableSensitiveDataLogging()
            .LogTo(Console.WriteLine, LogLevel.Information);
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // modelBuilder.Entity<Customer>()
        //     .ToTable("TBL_CUSTOMER")
        //     .HasKey(c => c.Id);
        //
        // modelBuilder.Entity<Customer>()
        //     .Property(c => c.Name)
        //     .HasColumnName("COL_CUSTOMER_NAME")
        //     .IsRequired(false);

        modelBuilder.Entity<Customer>()
            .Property(c => c.TotalRevenue)
            .HasPrecision(18, 2);

        // variant 1: map to two tables
        // modelBuilder.Entity<Customer>()
        //     .HasOne(c => c.Address)
        //     .WithOne()
        //     .HasForeignKey<Customer>()
        //     .IsRequired(false);

        // variant 2: embedded
        modelBuilder.Entity<Customer>()
            .OwnsOne(c => c.Address);

        modelBuilder.Entity<Customer>()
            .HasMany(c => c.Orders)
            .WithOne(o => o.Customer);

    }

    public DbSet<Customer> Customers => Set<Customer>();
    public DbSet<Order> Orders => Set<Order>();
}