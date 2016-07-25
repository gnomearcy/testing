package hr.span.tmartincic.custom_couch_sync;

public class Product
{
    public int Id;
    public String Name;
    public String Category;
    public Float Price;

    public Product(int id, String name, String category, Float price)
    {
        this.Id = id;
        this.Name = name;
        this.Category = category;
        this.Price = price;
    }
}
