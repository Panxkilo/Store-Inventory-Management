package com.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia del inventario en archivo JSON usando Gson.
 * Son metodos estaticos porque solo transforman datos (no guardan estado propio).
 */
public class InventoryStorage {

    // pretty printing para que el JSON quede legible al abrirlo.
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Tipo de la lista de productos, necesario para que Gson deserialice el JSON.
    private static final Type PRODUCT_LIST_TYPE = new TypeToken<List<Product>>() {}.getType();

    /** Serializa la lista de productos del inventario a un archivo JSON. */
    public static void saveInventoryToFile(StoreInventory inventory, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            GSON.toJson(inventory.getProducts(), writer);
        }
    }

    /** Lee el archivo JSON, reconstruye los Product y devuelve un StoreInventory poblado. */
    public static StoreInventory loadInventoryFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            List<Product> products = GSON.fromJson(reader, PRODUCT_LIST_TYPE);

            StoreInventory inventory = new StoreInventory();
            // Si el archivo estaba vacio, Gson devuelve null: dejamos el inventario vacio.
            if (products != null) {
                for (Product product : products) {
                    inventory.addProduct(product);
                }
            }
            return inventory;
        }
    }

    // No se instancia: es solo un contenedor de metodos utilitarios.
    private InventoryStorage() {
    }
}
