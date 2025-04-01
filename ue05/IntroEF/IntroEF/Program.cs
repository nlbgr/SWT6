using IntroEF.Dal;
using IntroEF.Utils;
using static IntroEF.Utils.PrintUtil;

PrintTitle("Creating database OrderDb", character: '=');

await using (var db = new OrderManagementContext())
{
    await DatabaseUtil.CreateDatabaseAsync(db, recreate: true);
}


PrintTitle("Adding Customers", character: '=');
await Commands.AddCustomersAsync();

PrintTitle("List all Customers", character: '=');
await Commands.ListCustomersAsync();




PrintTitle("Adding Orders", character: '=');
await Commands.AddOrdersAsync();

PrintTitle("List all Customers", character: '=');
await Commands.ListCustomersAsync();
