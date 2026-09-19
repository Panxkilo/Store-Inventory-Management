package com.store;

import java.io.IOException;
import java.util.List;

/**
 * Demo del flujo completo del sistema de inventario y ordenes.
 * Cada bloque corresponde a un punto del enunciado.
 */
public class Main {

    public static void main(String[] args) {
        StoreInventory inventory = new StoreInventory();

        // 1. Agregar varios productos.
        System.out.println("=== 1. Agregar productos ===");
        inventory.addProduct(new Product("Laptop", "Electronics", 999.99, 5));
        inventory.addProduct(new Product("Mouse", "Electronics", 25.50, 40));
        inventory.addProduct(new Product("Desk Chair", "Furniture", 149.90, 12));
        inventory.listInventory();

        // 2. Casos invalidos: la validacion lanza excepcion y aca la manejamos.
        System.out.println("\n=== 2. Casos invalidos (se espera que fallen) ===");
        try {
            new Product("Broken Price", "Test", -10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepcion manejada -> " + e.getMessage());
        }
        try {
            new Product("Broken Stock", "Test", 10.0, -3);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepcion manejada -> " + e.getMessage());
        }

        // 3. Procesar una orden con descuento por volumen.
        System.out.println("\n=== 3. Orden con descuento ===");
        // 2 laptops -> subtotal 1999.98 -> cae en el tramo >= $500 (15%).
        OrderSummary summary = inventory.createOrder("Laptop", 2);
        System.out.println("Resumen de la orden: " + summary);
        System.out.println("Stock tras la orden:");
        inventory.listInventory();

        // 4. Guardar el inventario a archivo.
        System.out.println("\n=== 4. Guardar inventario ===");
        String filePath = "inventory.json";
        try {
            InventoryStorage.saveInventoryToFile(inventory, filePath);
            System.out.println("Inventario guardado en: " + filePath);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }

        // 5. Cargar el inventario desde archivo y verificar que se reconstruyo.
        System.out.println("\n=== 5. Cargar inventario desde archivo ===");
        try {
            StoreInventory loaded = InventoryStorage.loadInventoryFromFile(filePath);
            System.out.println("Inventario reconstruido desde el archivo:");
            loaded.listInventory();

            // Pequena verificacion extra: buscar por categoria sobre lo cargado.
            List<Product> electronics = loaded.searchByCategory("Electronics");
            System.out.println("Productos en 'Electronics': " + electronics.size());
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }
}
