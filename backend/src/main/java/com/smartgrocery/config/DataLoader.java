package com.smartgrocery.config;

import com.smartgrocery.model.Product;
import com.smartgrocery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        String baseImg = "https://images.unsplash.com/photo-";
        List<Product> products = List.of(
                // Fruits
                product("Apple Red", "Fresh red apples, crisp and sweet", 120.0, "Fruits", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 100),
                product("Banana", "Ripe yellow bananas, rich in potassium", 60.0, "Fruits", baseImg + "1571771894821-ce9b6b11a125?w=400", 150),
                product("Orange", "Juicy oranges, high in Vitamin C", 80.0, "Fruits", baseImg + "1547514701-2c58105c19fb?w=400", 120),
                product("Mango Alphonso", "Premium Alphonso mangoes, sweet and pulpy", 200.0, "Fruits", baseImg + "1553279768-2aafb2e8e8c8?w=400", 80),
                product("Grapes Green", "Fresh seedless green grapes", 150.0, "Fruits", baseImg + "1596363505726-1a565b2c6a8a?w=400", 60),
                product("Watermelon", "Sweet and refreshing watermelon", 40.0, "Fruits", baseImg + "1589984667336-7d64a2d430c8?w=400", 50),
                product("Pomegranate", "Ruby red pomegranate seeds", 180.0, "Fruits", baseImg + "1595855759922-2d472069a966?w=400", 70),
                product("Papaya", "Ripe papaya, rich in enzymes", 50.0, "Fruits", baseImg + "1558642452-8e735e9c8b8b?w=400", 90),
                product("Pineapple", "Sweet tropical pineapple", 90.0, "Fruits", baseImg + "1550258981-5cdeac8a8b8b?w=400", 55),
                product("Strawberry", "Fresh strawberries, rich in antioxidants", 250.0, "Fruits", baseImg + "1464969547264-0cbb2d2b8b8b?w=400", 40),
                product("Kiwi", "Green kiwi fruit, Vitamin C rich", 200.0, "Fruits", baseImg + "1585050812-8b8b8b8b8b8b?w=400", 65),
                product("Guava", "Fresh green guava", 70.0, "Fruits", baseImg + "1595855759922-2d472069a966?w=400", 85),
                product("Chikoo", "Sweet sapota fruit", 80.0, "Fruits", baseImg + "1558642452-8e735e9c8b8b?w=400", 75),
                product("Custard Apple", "Creamy custard apple", 120.0, "Fruits", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 45),
                product("Blueberries", "Fresh blueberries pack", 300.0, "Fruits", baseImg + "1498557850523-fa3dde9d8b8b?w=400", 30),
                product("Muskmelon", "Sweet muskmelon", 55.0, "Fruits", baseImg + "1589984667336-7d64a2d430c8?w=400", 60),
                product("Litchi", "Fresh litchi, seasonal", 180.0, "Fruits", baseImg + "1596363505726-1a565b2c6a8a?w=400", 35),
                product("Pear", "Green pear, crisp and juicy", 140.0, "Fruits", baseImg + "1547514701-2c58105c19fb?w=400", 50),
                product("Plum", "Red plums", 160.0, "Fruits", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 45),
                product("Cherry", "Fresh red cherries", 400.0, "Fruits", baseImg + "1528821122-8b8b8b8b8b8b?w=400", 25),
                // Vegetables
                product("Tomato", "Fresh red tomatoes", 50.0, "Vegetables", baseImg + "1546470422-6a9b8b8b8b8b?w=400", 200),
                product("Onion", "Fresh onions, 1 kg", 40.0, "Vegetables", baseImg + "1518977676601-b53f82aba655?w=400", 150),
                product("Potato", "Fresh potatoes, 1 kg", 35.0, "Vegetables", baseImg + "1518977676601-b53f82aba655?w=400", 180),
                product("Brinjal", "Purple brinjal", 60.0, "Vegetables", baseImg + "1595855759922-2d472069a966?w=400", 70),
                product("Capsicum Green", "Green bell pepper", 80.0, "Vegetables", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 90),
                product("Carrot", "Fresh carrots, 500g", 45.0, "Vegetables", baseImg + "1598177446313-1a585b2c6a8a?w=400", 100),
                product("Cauliflower", "Fresh cauliflower", 55.0, "Vegetables", baseImg + "1571771894821-ce9b6b11a125?w=400", 80),
                product("Cabbage", "Green cabbage", 30.0, "Vegetables", baseImg + "1547514701-2c58105c19fb?w=400", 95),
                product("Spinach", "Fresh spinach leaves, 250g", 35.0, "Vegetables", baseImg + "1576048699432-2d472069a966?w=400", 120),
                product("Coriander", "Fresh coriander bunch", 20.0, "Vegetables", baseImg + "1596363505726-1a565b2c6a8a?w=400", 150),
                product("Mint", "Fresh mint leaves", 25.0, "Vegetables", baseImg + "1595855759922-2d472069a966?w=400", 80),
                product("Green Beans", "Fresh French beans", 90.0, "Vegetables", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 60),
                product("Peas", "Green peas, 500g", 70.0, "Vegetables", baseImg + "1589984667336-7d64a2d430c8?w=400", 75),
                product("Cucumber", "Fresh cucumber", 30.0, "Vegetables", baseImg + "1604978847750-8b8b8b8b8b8b?w=400", 100),
                product("Lady Finger", "Fresh okra", 65.0, "Vegetables", baseImg + "1596363505726-1a565b2c6a8a?w=400", 85),
                product("Bottle Gourd", "Fresh lauki", 40.0, "Vegetables", baseImg + "1558642452-8e735e9c8b8b?w=400", 70),
                product("Ridge Gourd", "Fresh tori", 45.0, "Vegetables", baseImg + "1547514701-2c58105c19fb?w=400", 65),
                product("Broccoli", "Fresh broccoli", 120.0, "Vegetables", baseImg + "1571771894821-ce9b6b11a125?w=400", 55),
                product("Zucchini", "Fresh zucchini", 85.0, "Vegetables", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 50),
                product("Lemon", "Fresh lemons, 500g", 50.0, "Vegetables", baseImg + "1547514701-2c58105c19fb?w=400", 130),
                product("Ginger", "Fresh ginger, 250g", 60.0, "Vegetables", baseImg + "1595855759922-2d472069a966?w=400", 90),
                product("Garlic", "Fresh garlic, 200g", 55.0, "Vegetables", baseImg + "1576048699432-2d472069a966?w=400", 95),
                product("Sweet Corn", "Fresh sweet corn", 45.0, "Vegetables", baseImg + "1589984667336-7d64a2d430c8?w=400", 80),
                product("Radish", "Fresh white radish", 35.0, "Vegetables", baseImg + "1598177446313-1a585b2c6a8a?w=400", 70),
                product("Beetroot", "Fresh beetroot", 50.0, "Vegetables", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 60),
                product("Pumpkin", "Fresh pumpkin", 40.0, "Vegetables", baseImg + "1589984667336-7d64a2d430c8?w=400", 55),
                product("Bitter Gourd", "Fresh karela", 70.0, "Vegetables", baseImg + "1596363505726-1a565b2c6a8a?w=400", 45),
                product("Snake Gourd", "Fresh padwal", 55.0, "Vegetables", baseImg + "1558642452-8e735e9c8b8b?w=400", 50),
                product("Drumstick", "Fresh drumsticks", 60.0, "Vegetables", baseImg + "1547514701-2c58105c19fb?w=400", 65),
                product("Fenugreek", "Fresh methi leaves", 40.0, "Vegetables", baseImg + "1576048699432-2d472069a966?w=400", 75),
                product("Spring Onion", "Fresh spring onion", 35.0, "Vegetables", baseImg + "1518977676601-b53f82aba655?w=400", 85),
                // Dairy
                product("Amul Milk", "Full cream milk 1L", 60.0, "Dairy", baseImg + "1550583724-b9502ee9c8b8b?w=400", 100),
                product("Curd", "Fresh curd 500g", 45.0, "Dairy", baseImg + "1576048699432-2d472069a966?w=400", 80),
                product("Butter", "Amul butter 500g", 275.0, "Dairy", baseImg + "1589984667336-7d64a2d430c8?w=400", 60),
                product("Cheese Slice", "Processed cheese slices", 120.0, "Dairy", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 70),
                product("Paneer", "Fresh paneer 200g", 90.0, "Dairy", baseImg + "1595855759922-2d472069a966?w=400", 85),
                product("Cream", "Fresh cream 200ml", 85.0, "Dairy", baseImg + "1550583724-b9502ee9c8b8b?w=400", 55),
                product("Ghee", "Pure cow ghee 500g", 450.0, "Dairy", baseImg + "1589984667336-7d64a2d430c8?w=400", 40),
                product("Skimmed Milk", "Toned milk 1L", 52.0, "Dairy", baseImg + "1550583724-b9502ee9c8b8b?w=400", 90),
                product("Mozzarella", "Pizza cheese 200g", 180.0, "Dairy", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 50),
                product("Yogurt", "Flavored yogurt 400g", 55.0, "Dairy", baseImg + "1576048699432-2d472069a966?w=400", 75),
                product("Lassi", "Sweet lassi 500ml", 40.0, "Dairy", baseImg + "1550583724-b9502ee9c8b8b?w=400", 65),
                product("Condensed Milk", "Milkmaid 400g", 95.0, "Dairy", baseImg + "1589984667336-7d64a2d430c8?w=400", 45),
                product("Cottage Cheese", "Paneer block 400g", 180.0, "Dairy", baseImg + "1595855759922-2d472069a966?w=400", 35),
                product("Ice Cream", "Vanilla 500ml", 200.0, "Dairy", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 40),
                product("Milk Powder", "Full cream 1kg", 550.0, "Dairy", baseImg + "1550583724-b9502ee9c8b8b?w=400", 30),
                // Snacks
                product("Lays Classic", "Potato chips 52g", 20.0, "Snacks", baseImg + "1567620832993-8b8b8b8b8b8b?w=400", 200),
                product("Kurkure", "Masala munch 55g", 20.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 180),
                product("Bingo Chips", "Tangy tomato 60g", 20.0, "Snacks", baseImg + "1567620832993-8b8b8b8b8b8b?w=400", 170),
                product("Parle-G", "Glucose biscuits 100g", 10.0, "Snacks", baseImg + "1558642452-8e735e9c8b8b?w=400", 250),
                product("Marie Gold", "Britannia marie 150g", 30.0, "Snacks", baseImg + "1547514701-2c58105c19fb?w=400", 150),
                product("Good Day", "Butter cookies 200g", 45.0, "Snacks", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 120),
                product("Oreo", "Chocolate cookies 154g", 30.0, "Snacks", baseImg + "1595855759922-2d472069a966?w=400", 140),
                product("Haldiram Bhujia", "Classic bhujia 200g", 50.0, "Snacks", baseImg + "1576048699432-2d472069a966?w=400", 100),
                product("Aloo Bhujia", "Haldiram aloo bhujia 400g", 90.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 80),
                product("Chocolate", "Dairy Milk 60g", 50.0, "Snacks", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 130),
                product("Munch", "Chocolate bar 47g", 20.0, "Snacks", baseImg + "1596363505726-1a565b2c6a8a?w=400", 160),
                product("Bourbon", "Chocolate cream biscuits", 30.0, "Snacks", baseImg + "1547514701-2c58105c19fb?w=400", 110),
                product("Nutri Choice", "Digestive biscuits 200g", 55.0, "Snacks", baseImg + "1558642452-8e735e9c8b8b?w=400", 95),
                product("Popcorn", "Ready to pop 100g", 45.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 70),
                product("Peanuts", "Roasted peanuts 200g", 80.0, "Snacks", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 85),
                product("Cashew", "Premium cashew 200g", 380.0, "Snacks", baseImg + "1595855759922-2d472069a966?w=400", 50),
                product("Almonds", "California almonds 200g", 320.0, "Snacks", baseImg + "1576048699432-2d472069a966?w=400", 55),
                product("Raisins", "Black raisins 500g", 180.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 60),
                product("Pringles", "Original 107g", 99.0, "Snacks", baseImg + "1567620832993-8b8b8b8b8b8b?w=400", 75),
                product("Cheese Balls", "Cheese flavored 85g", 50.0, "Snacks", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 90),
                product("Murukku", "South Indian murukku 200g", 85.0, "Snacks", baseImg + "1596363505726-1a565b2c6a8a?w=400", 65),
                product("Mixture", "South Indian mixture 250g", 95.0, "Snacks", baseImg + "1547514701-2c58105c19fb?w=400", 70),
                product("Dark Fantasy", "Choco fill biscuits", 100.0, "Snacks", baseImg + "1558642452-8e735e9c8b8b?w=400", 55),
                product("Hide & Seek", "Chocolate biscuits", 30.0, "Snacks", baseImg + "1595855759922-2d472069a966?w=400", 100),
                product("50-50", "Sweet and salt biscuits", 20.0, "Snacks", baseImg + "1576048699432-2d472069a966?w=400", 120),
                product("Unibic Cookies", "Butter cookies 300g", 120.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 45),
                product("Maggie Noodles", "Masala 70g", 12.0, "Snacks", baseImg + "1567620832993-8b8b8b8b8b8b?w=400", 300),
                product("Top Ramen", "Curry 70g", 15.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 250),
                product("Yippee Noodles", "Magic masala 63g", 15.0, "Snacks", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 200),
                product("Bikano Soan Papdi", "500g box", 180.0, "Snacks", baseImg + "1596363505726-1a565b2c6a8a?w=400", 40),
                product("Rusk", "Britannia rusk 400g", 55.0, "Snacks", baseImg + "1547514701-2c58105c19fb?w=400", 80),
                // Beverages
                product("Coca Cola", "Soft drink 2L", 90.0, "Beverages", baseImg + "1550583724-b9502ee9c8b8b?w=400", 100),
                product("Pepsi", "Cold drink 2L", 90.0, "Beverages", baseImg + "1589984667336-7d64a2d430c8?w=400", 95),
                product("Frooti", "Mango drink 1L", 60.0, "Beverages", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 85),
                product("Real Juice", "Mixed fruit 1L", 120.0, "Beverages", baseImg + "1595855759922-2d472069a966?w=400", 70),
                product("Tropicana", "Orange juice 1L", 180.0, "Beverages", baseImg + "1576048699432-2d472069a966?w=400", 60),
                product("Bisleri", "Packaged water 1L", 20.0, "Beverages", baseImg + "1550583724-b9502ee9c8b8b?w=400", 200),
                product("Red Bull", "Energy drink 250ml", 115.0, "Beverages", baseImg + "1589984667336-7d64a2d430c8?w=400", 80),
                product("Nescafe", "Instant coffee 100g", 220.0, "Beverages", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 90),
                product("Tata Tea", "Premium tea 500g", 280.0, "Beverages", baseImg + "1596363505726-1a565b2c6a8a?w=400", 75),
                product("Brooke Bond", "Red label 500g", 250.0, "Beverages", baseImg + "1547514701-2c58105c19fb?w=400", 85),
                product("Horlicks", "Health drink 1kg", 550.0, "Beverages", baseImg + "1558642452-8e735e9c8b8b?w=400", 50),
                product("Bournvita", "Chocolate drink 500g", 295.0, "Beverages", baseImg + "1595855759922-2d472069a966?w=400", 65),
                product("Complan", "Nutrition drink 500g", 350.0, "Beverages", baseImg + "1576048699432-2d472069a966?w=400", 55),
                product("Sprite", "Lemon drink 2L", 90.0, "Beverages", baseImg + "1550583724-b9502ee9c8b8b?w=400", 90),
                product("Maaza", "Mango drink 600ml", 40.0, "Beverages", baseImg + "1589984667336-7d64a2d430c8?w=400", 110),
                product("Slice", "Mango 600ml", 40.0, "Beverages", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 100),
                product("Minute Maid", "Orange 1L", 95.0, "Beverages", baseImg + "1596363505726-1a565b2c6a8a?w=400", 70),
                product("Appy", "Apple drink 600ml", 50.0, "Beverages", baseImg + "1547514701-2c58105c19fb?w=400", 85),
                product("Thumbs Up", "Strong cola 2L", 90.0, "Beverages", baseImg + "1558642452-8e735e9c8b8b?w=400", 80),
                product("Limca", "Lemon lime 2L", 90.0, "Beverages", baseImg + "1595855759922-2d472069a966?w=400", 75),
                product("Nimbooz", "Lemon drink 500ml", 35.0, "Beverages", baseImg + "1576048699432-2d472069a966?w=400", 95),
                product("Green Tea", "Tata tea 25 bags", 150.0, "Beverages", baseImg + "1589984667336-7d64a2d430c8?w=400", 60),
                product("Bru", "Coffee 200g", 240.0, "Beverages", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 70),
                product("Chocolate Powder", "Cadbury drinking choco 500g", 320.0, "Beverages", baseImg + "1596363505726-1a565b2c6a8a?w=400", 45),
                product("Tang", "Orange drink 500g", 185.0, "Beverages", baseImg + "1547514701-2c58105c19fb?w=400", 65),
                product("Gatorade", "Sports drink 500ml", 85.0, "Beverages", baseImg + "1558642452-8e735e9c8b8b?w=400", 55),
                product("Paper Boat", "Kokum 250ml", 30.0, "Beverages", baseImg + "1595855759922-2d472069a966?w=400", 90),
                product("Lemonade", "Fresh lemonade 1L", 80.0, "Beverages", baseImg + "1576048699432-2d472069a966?w=400", 50),
                // Staples
                product("Basmati Rice", "Premium 1kg", 120.0, "Staples", baseImg + "1589984667336-7d64a2d430c8?w=400", 80),
                product("Wheat Flour", "Aashirvaad 1kg", 55.0, "Staples", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 100),
                product("Toor Dal", "Split pigeon pea 1kg", 140.0, "Staples", baseImg + "1595855759922-2d472069a966?w=400", 70),
                product("Moong Dal", "Yellow moong 1kg", 130.0, "Staples", baseImg + "1576048699432-2d472069a966?w=400", 75),
                product("Chana Dal", "Split chickpea 1kg", 110.0, "Staples", baseImg + "1547514701-2c58105c19fb?w=400", 85),
                product("Urad Dal", "Black gram 500g", 95.0, "Staples", baseImg + "1558642452-8e735e9c8b8b?w=400", 65),
                product("Rajma", "Kidney beans 500g", 85.0, "Staples", baseImg + "1596363505726-1a565b2c6a8a?w=400", 90),
                product("Chickpea", "Kabuli chana 1kg", 100.0, "Staples", baseImg + "1589984667336-7d64a2d430c8?w=400", 70),
                product("Sugar", "Refined sugar 1kg", 50.0, "Staples", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 120),
                product("Salt", "Iodized salt 1kg", 25.0, "Staples", baseImg + "1595855759922-2d472069a966?w=400", 150),
                product("Cooking Oil", "Sunflower oil 1L", 180.0, "Staples", baseImg + "1576048699432-2d472069a966?w=400", 95),
                product("Mustard Oil", "Pure 1L", 190.0, "Staples", baseImg + "1547514701-2c58105c19fb?w=400", 60),
                product("Atta", "Whole wheat 5kg", 250.0, "Staples", baseImg + "1558642452-8e735e9c8b8b?w=400", 55),
                product("Sooji", "Semolina 500g", 45.0, "Staples", baseImg + "1596363505726-1a565b2c6a8a?w=400", 80),
                product("Vermicelli", "Thin seviyan 500g", 60.0, "Staples", baseImg + "1589984667336-7d64a2d430c8?w=400", 70),
                product("Poha", "Flattened rice 500g", 40.0, "Staples", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 85),
                product("Rice Flour", "Chawal ka atta 500g", 55.0, "Staples", baseImg + "1595855759922-2d472069a966?w=400", 65),
                product("Besan", "Gram flour 500g", 65.0, "Staples", baseImg + "1576048699432-2d472069a966?w=400", 75),
                product("Jaggery", "Organic jaggery 500g", 80.0, "Staples", baseImg + "1547514701-2c58105c19fb?w=400", 55),
                product("Honey", "Pure honey 500g", 350.0, "Staples", baseImg + "1558642452-8e735e9c8b8b?w=400", 40),
                product("Vinegar", "White vinegar 500ml", 45.0, "Staples", baseImg + "1596363505726-1a565b2c6a8a?w=400", 60),
                product("Soy Sauce", "Dark soy 200ml", 85.0, "Staples", baseImg + "1589984667336-7d64a2d430c8?w=400", 50),
                product("Tomato Ketchup", "Kissan 1kg", 185.0, "Staples", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 45),
                product("Red Chilli Powder", "MDH 100g", 55.0, "Staples", baseImg + "1595855759922-2d472069a966?w=400", 90),
                product("Turmeric", "Haldi powder 200g", 80.0, "Staples", baseImg + "1576048699432-2d472069a966?w=400", 70),
                product("Cumin", "Jeera 100g", 75.0, "Staples", baseImg + "1547514701-2c58105c19fb?w=400", 65),
                product("Coriander Powder", "Dhania 100g", 60.0, "Staples", baseImg + "1558642452-8e735e9c8b8b?w=400", 80),
                product("Garam Masala", "MDH 50g", 65.0, "Staples", baseImg + "1596363505726-1a565b2c6a8a?w=400", 75),
                product("Black Pepper", "Kali mirch 50g", 120.0, "Staples", baseImg + "1589984667336-7d64a2d430c8?w=400", 55),
                product("Mustard Seeds", "Rai 100g", 45.0, "Staples", baseImg + "1560804652-5e9e5a0b9c8b?w=400", 70),
                product("Dry Red Chilli", "Whole 100g", 50.0, "Staples", baseImg + "1595855759922-2d472069a966?w=400", 85)
        );

        List<Product> additionalProducts = List.of(
                product("Lays Classic", "Classic salted chips", 20.0, "Snacks", baseImg + "1567620832993-8b8b8b8b8b8b?w=400", 120),
                product("Kurkure", "Masala munch snack pouch", 22.0, "Snacks", baseImg + "1589984667336-7d64a2d430c8?w=400", 110),
                product("Bingo", "Tangy tomato chips", 18.0, "Snacks", baseImg + "1556912173-9c8b8b8b8b8b?w=400", 100),
                product("Doritos", "Cool ranch tortilla chips", 35.0, "Snacks", baseImg + "1565299624946-34b5fbd7f0d8?w=400", 90),
                product("Pringles", "Original stackable chips", 99.0, "Snacks", baseImg + "1529042411864-08a7c1ae06b5?w=400", 85),
                product("Haldiram Snacks", "Mixed namkeen snack pack", 60.0, "Snacks", baseImg + "1572495671-7a0d3f8f0d8b?w=400", 70),
                product("Namkeen", "Crunchy savory snack mix", 55.0, "Snacks", baseImg + "1509440159596-9c7e6a3b9f5d?w=400", 80),
                product("Chips", "Crunchy potato chips", 25.0, "Snacks", baseImg + "1601004890681-7d4d9d9b7a1e?w=400", 95),

                product("Parle-G", "Classic glucose biscuits", 15.0, "Biscuits & Cookies", baseImg + "1556912173-9c8b8b8b8b8b?w=400", 150),
                product("Good Day", "Butter cookies pack", 45.0, "Biscuits & Cookies", baseImg + "1565299624946-34b5fbd7f0d8?w=400", 130),
                product("Oreo", "Chocolate cream biscuits", 35.0, "Biscuits & Cookies", baseImg + "1529042411864-08a7c1ae06b5?w=400", 120),
                product("Bourbon", "Chocolate biscuit pack", 40.0, "Biscuits & Cookies", baseImg + "1572495671-7a0d3f8f0d8b?w=400", 110),
                product("Marie Gold", "Classic sweet biscuits", 30.0, "Biscuits & Cookies", baseImg + "1509440159596-9c7e6a3b9f5d?w=400", 100),
                product("Hide & Seek", "Chocolate cookies", 32.0, "Biscuits & Cookies", baseImg + "1601004890681-7d4d9d9b7a1e?w=400", 95),

                product("Tata Tea", "Premium tea leaves 500g", 280.0, "Tea & Coffee", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 80),
                product("Red Label", "Classic tea blend", 260.0, "Tea & Coffee", baseImg + "1517705002910-5ca5a0732f5a?w=400", 75),
                product("Taj Mahal Tea", "Rich aromatic tea", 300.0, "Tea & Coffee", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 70),
                product("Bru Coffee", "Instant coffee powder", 240.0, "Tea & Coffee", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 65),
                product("Nescafe", "Classic instant coffee", 230.0, "Tea & Coffee", baseImg + "1517705002910-5ca5a0732f5a?w=400", 60),
                product("Continental Coffee", "Roasted coffee beans", 320.0, "Tea & Coffee", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 50),

                product("Amul Ice Cream", "Classic vanilla tub", 180.0, "Ice Creams", baseImg + "1570197788419-0d7a4b0d5c6b?w=400", 60),
                product("Kwality Wall's", "Chocolate cone pack", 160.0, "Ice Creams", baseImg + "1516559828984-fb3b99548b21?w=400", 55),
                product("Cornetto", "Crunchy cone ice cream", 70.0, "Ice Creams", baseImg + "1482049016688-2d3e1b311543?w=400", 70),
                product("Magnum", "Belgian chocolate ice cream", 120.0, "Ice Creams", baseImg + "1516559828984-fb3b99548b21?w=400", 50),
                product("Vanilla", "Creamy vanilla ice cream", 150.0, "Ice Creams", baseImg + "1482049016688-2d3e1b311543?w=400", 65),
                product("Chocolate", "Rich chocolate ice cream", 155.0, "Ice Creams", baseImg + "1570197788419-0d7a4b0d5c6b?w=400", 60),
                product("Butterscotch", "Sweet butterscotch tub", 165.0, "Ice Creams", baseImg + "1516559828984-fb3b99548b21?w=400", 45),

                product("Frozen Peas", "Green peas 500g", 80.0, "Frozen Foods", baseImg + "1512621776951-a57141f2eefd?w=400", 100),
                product("Frozen Corn", "Sweet corn kernels 500g", 85.0, "Frozen Foods", baseImg + "1512621776951-a57141f2eefd?w=400", 95),
                product("French Fries", "Ready-to-cook fries 500g", 95.0, "Frozen Foods", baseImg + "1541592106381-bb71ad8a3e9f?w=400", 90),
                product("Veg Nuggets", "Crispy vegetable nuggets", 110.0, "Frozen Foods", baseImg + "1541592106381-bb71ad8a3e9f?w=400", 85),
                product("Frozen Pizza", "Cheese and veggie pizza", 260.0, "Frozen Foods", baseImg + "1512621776951-a57141f2eefd?w=400", 50),
                product("Frozen Momos", "Steam-ready vegetable momos", 150.0, "Frozen Foods", baseImg + "1541592106381-bb71ad8a3e9f?w=400", 70),

                product("Maggi", "Masala noodles 70g", 14.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 140),
                product("Yippee Noodles", "Classic noodles pack", 16.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 130),
                product("Pasta", "Elbow pasta 500g", 90.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 110),
                product("Oats", "Instant oats 500g", 85.0, "Packaged Foods", baseImg + "1512621776951-a57141f2eefd?w=400", 100),
                product("Corn Flakes", "Breakfast cereal 500g", 110.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 80),
                product("Muesli", "Healthy cereal mix", 180.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 75),
                product("Ready-to-Eat Meals", "Instant meal pack", 95.0, "Packaged Foods", baseImg + "1513442542250-6c2f4b4d53a0?w=400", 70),

                product("Bread", "Fresh loaf of bread", 42.0, "Bakery", baseImg + "1509440159596-9c7e6a3b9f5d?w=400", 100),
                product("Buns", "Soft burger buns", 35.0, "Bakery", baseImg + "1509440159596-9c7e6a3b9f5d?w=400", 90),
                product("Cakes", "Fresh cream cake", 260.0, "Bakery", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 50),
                product("Muffins", "Chocolate muffins", 60.0, "Bakery", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 70),
                product("Donuts", "Glazed donuts pack", 80.0, "Bakery", baseImg + "1509440159596-9c7e6a3b9f5d?w=400", 60),
                product("Croissants", "Buttery croissants", 70.0, "Bakery", baseImg + "1495474472287-6d71b5f7b0b9?w=400", 55),

                product("Detergent", "Laundry detergent 1kg", 160.0, "Household Essentials", baseImg + "1517705002910-5ca5a0732f5a?w=400", 80),
                product("Soap", "Bathing soap pack", 55.0, "Household Essentials", baseImg + "1517705002910-5ca5a0732f5a?w=400", 120),
                product("Dishwash Liquid", "Kitchen cleaner 500ml", 90.0, "Household Essentials", baseImg + "1517705002910-5ca5a0732f5a?w=400", 100),
                product("Tissue Paper", "Soft tissue paper pack", 75.0, "Household Essentials", baseImg + "1517705002910-5ca5a0732f5a?w=400", 110),
                product("Cleaning Supplies", "Multi-surface cleaner", 130.0, "Household Essentials", baseImg + "1517705002910-5ca5a0732f5a?w=400", 90)
        );

        List<Product> allProducts = new ArrayList<>(products);
        allProducts.addAll(additionalProducts);

        Set<String> existingNames = productRepository.findAll().stream()
                .map(Product::getName)
                .collect(Collectors.toSet());

        if (productRepository.count() == 0) {
            productRepository.saveAll(allProducts);
        } else {
            List<Product> missingProducts = allProducts.stream()
                    .filter(product -> !existingNames.contains(product.getName()))
                    .toList();

            if (!missingProducts.isEmpty()) {
                productRepository.saveAll(missingProducts);
            }
        }
    }

    private Product product(String name, String desc, double price, String category, String img, int stock) {
        return Product.builder()
                .name(name)
                .description(desc)
                .price(price)
                .category(category)
                .imageUrl(img)
                .rating(3.5 + (Math.random() * 1.5)) // simple 3.5 - 5.0
                .stock(stock)
                .build();
    }
}
