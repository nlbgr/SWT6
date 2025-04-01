using System.ComponentModel.DataAnnotations;

namespace IntroEF.Domain;

public class Order(Guid id, string article, DateTimeOffset orderDate, decimal totalPrice)
{
    public Order(string article, DateTimeOffset orderDate, decimal totalPrice) :
      this(Guid.NewGuid(), article, orderDate, totalPrice)
    {
    }

    public Guid Id { get; set; } = id;

    public string Article { get; set; } = article;

    public DateTimeOffset OrderDate { get; set; } = orderDate;

    public decimal TotalPrice { get; set; } = totalPrice;

    // variante 1: Temporäre null value
    // [Required]
    // public Customer? Customer { get; set; }
    
    // variante 2: mit nullable backing element
    private Customer? customer;
    public required Customer Customer
    {
        get => customer ?? throw new InvalidOperationException("Required Property Customer is null");
        set
        {
            customer = value ?? throw new ArgumentNullException(nameof(Customer));
            customer.Orders.Add(this);
        }
    }

    public override string ToString() => $"Order {{ Id: {Id}, Article: {Article}, OrderDate: {OrderDate:d}, TotalPrice: {TotalPrice:F2} }}";
}
