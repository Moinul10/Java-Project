package CSE124;
import java.util.*;

// Product class
class Product {
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String name, String category, double price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

// Cart class
class Cart {
    private Map<Product, Integer> cartItems;
    private double totalPrice;

    public Cart() {
        cartItems = new LinkedHashMap<>();
        totalPrice = 0;
    }

    public void addProduct(Product product) {
        if (product.getStock() > 0) {
            cartItems.put(product, cartItems.getOrDefault(product, 0) + 1);
            totalPrice += product.getPrice();
            product.setStock(product.getStock() - 1);
            System.out.println(product.getName() + " added to cart.");
        } else {
            System.out.println("Sorry, " + product.getName() + " is out of stock.");
        }
    }

    public void removeProduct(String productName) {
        for (Product p : cartItems.keySet()) {
            if (p.getName().equalsIgnoreCase(productName)) {
                int quantity = cartItems.get(p);
                totalPrice -= p.getPrice();
                p.setStock(p.getStock() + 1);

                if (quantity == 1) {
                    cartItems.remove(p);
                } else {
                    cartItems.put(p, quantity - 1);
                }
                System.out.println(productName + " removed from cart.");
                return;
            }
        }
        System.out.println(productName + " not found in cart.");
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\nItems in Cart:");
        int i = 1;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product item = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = item.getPrice() * quantity;
            System.out.printf("%d. %s - Qty: %d x $%.2f = $%.2f\n", i++, item.getName(), quantity, item.getPrice(), itemTotal);
        }
        System.out.printf("Total Price (before discount): $%.2f\n", totalPrice);
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty. Add products before checkout.");
            return;
        }

        double discount = 0;
        if (totalPrice > 500) {
            discount = totalPrice * 0.10;
            System.out.println("A 10% discount has been applied!");
        } else if (totalPrice > 200) {
            discount = totalPrice * 0.05;
            System.out.println("A 5% discount has been applied!");
        }

        double finalPrice = totalPrice - discount;
        System.out.printf("Checkout successful! Final Amount: $%.2f\n", finalPrice);

        cartItems.clear();
        totalPrice = 0;
    }
}

// Main class
public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask for user name and welcome message
        System.out.print("Enter your name: ");
        String userName = sc.nextLine();
        System.out.println("Welcome, " + userName + "! Enjoy shopping with us!");

        // Create products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "Electronics", 800, 5));
        products.add(new Product("Phone", "Electronics", 500, 3));
        products.add(new Product("Headphones", "Accessories", 100, 10));
        products.add(new Product("Keyboard", "Accessories", 50, 8));
        products.add(new Product("Mouse", "Accessories", 30, 15));
        products.add(new Product("Monitor", "Electronics", 200, 4));
        products.add(new Product("Tablet", "Electronics", 300, 6));
        products.add(new Product("Charger", "Accessories", 25, 20));
        products.add(new Product("Smartwatch", "Electronics", 150, 5));
        products.add(new Product("USB Cable", "Accessories", 10, 25));

        Cart cart = new Cart();

        int choice;
        do {
            System.out.println("\n======= MENU =======");
            System.out.println("1. View Products by Category");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter category (Electronics / Accessories): ");
                    String cat = sc.nextLine();
                    System.out.println("\n" + cat + " Products:");
                    int count = 0;
                    for (int i = 0; i < products.size(); i++) {
                        Product p = products.get(i);
                        if (p.getCategory().equalsIgnoreCase(cat)) {
                            System.out.println((i + 1) + ". " + p.getName() + " - $" + p.getPrice() + " (Stock: " + p.getStock() + ")");
                            count++;
                        }
                    }
                    if (count == 0) {
                        System.out.println("No products found in this category.");
                    }
                    break;

                case 2:
                    System.out.print("Enter product number to add: ");
                    int addIndex = sc.nextInt();
                    if (addIndex >= 1 && addIndex <= products.size()) {
                        cart.addProduct(products.get(addIndex - 1));
                    } else {
                        System.out.println("Invalid product number.");
                    }
                    break;

                case 3:
                    System.out.print("Enter product name to remove: ");
                    String removeName = sc.nextLine();
                    cart.removeProduct(removeName);
                    break;

                case 4:
                    cart.displayCart();
                    break;

                case 5:
                    cart.checkout();
                    break;

                case 6:
                    System.out.println("Thank you for shopping, " + userName + ". Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
