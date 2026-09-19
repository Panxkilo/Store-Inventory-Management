package com.store;

/**
 * Representa un producto del inventario.
 * Los campos son privados y solo se acceden con getters/setters,
 * de modo que la validacion (precio y stock) siempre se aplica.
 */
public class Product {

    private String name;
    private String category;
    private double price;
    private int stockQuantity;

    public Product(String name, String category, double price, int stockQuantity) {
        this.name = name;
        this.category = category;
        // Se validan mediante los setters para no repetir las reglas de negocio.
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        // Regla de negocio: el precio debe ser estrictamente mayor a 0.
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0. Valor recibido: " + price);
        }
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        // Regla de negocio: el stock no puede quedar negativo.
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo. Valor recibido: " + stockQuantity);
        }
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        // Ejemplo: "Laptop (Electronics) - $999.99 | Stock: 5"
        // Locale.US para que el separador decimal sea siempre punto, sin importar el sistema.
        return String.format(java.util.Locale.US, "%s (%s) - $%.2f | Stock: %d",
                name, category, price, stockQuantity);
    }
}
