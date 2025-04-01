using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace IntroEF.Domain;

// [Table("TBL_CUSTOMERS")]
public class Customer(Guid id, string name, Rating rating)
{
    public Customer(string name, Rating rating) : this(Guid.NewGuid(), name, rating)
    {
    }

    // [Key]
    // [Column("CUSTOMER_ID")]
    public Guid Id { get; set; } = id;

    // [Column("COL_NAME")]
    public string Name { get; set; } = name;

    public Rating Rating { get; set; } = rating;

    // [Column(TypeName = "decimal(18,2)")]
    public decimal? TotalRevenue { get; set; }

    public Address? Address { get; set; }

    public IList<Order> Orders = [];

    public override string ToString() => $"Customer {{ Id: {Id}, Name: {Name}, " +
                                         $"TotalRevenue: {TotalRevenue} }}";
}
