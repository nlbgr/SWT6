namespace IntroEF.Dal;

using IntroEF.Domain;
using Microsoft.EntityFrameworkCore;

public static class Commands
{
    public static async Task AddCustomersAsync()
    {
        var customer1 = new Customer("Mayr Immobilien", Rating.B);
        customer1.Address = new Address(1234, "city", "street");

        var customer2 = new Customer("Software Solutions", Rating.A);

        await using var db = new OrderManagementContext();

        await db.AddRangeAsync(customer1, customer2);
        await db.SaveChangesAsync();
    }

    public static async Task ListCustomersAsync()
    {
        await using var db = new OrderManagementContext();

        var customers = await db.Customers
                                                .AsNoTracking()
                                                .Include(c => c.Address)
                                                .Include(c => c.Orders)
                                                .ToListAsync();

        foreach (var customer in customers)
        {
            Console.WriteLine(customer);

            if (customer.Address is not null)
            {
                Console.WriteLine($"\taddress: {customer.Address}");
            }

            if (customer.Orders.Any())
            {
                Console.WriteLine("\torders:");
                foreach (var order in customer.Orders)
                {
                    Console.WriteLine($"\t\t{order}");
                }
            }
        }
    }

    public static async Task AddOrdersAsync()
    {
        await using var db = new OrderManagementContext();
        var customer = db.Customers.FirstOrDefault();
        if (customer is null)
            return;

        var order1 = new Order("Surface Book 3", new DateTime(2022, 1, 1), 2850m) { Customer = customer };
        var order2 = new Order("Dell Monitor", new DateTime(2022, 2, 2), 250m) { Customer = customer };

        await db.Orders.AddRangeAsync(order1, order2);
        await db.SaveChangesAsync();
    }

    public static async Task UpdateTotalRevenuesAsync()
    {
        await Task.CompletedTask;
    }
}
