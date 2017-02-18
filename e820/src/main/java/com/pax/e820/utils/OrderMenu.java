package com.pax.e820.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pax.e820.R;
import com.pax.e820.model.ModelOrder;

public class OrderMenu {
	
	
	public static enum MenuCategory{
	    MENU_CAT_APPETIZERS,
	    MENU_CAT_DESSERTS,
	    MENU_CAT_GRILL,
	    MENU_CAT_PASTA,
	    MENU_CAT_SALADS,
	    MENU_CAT_SANDWICHES,
	    MENU_CAT_BURGERS,
	    MENU_CAT_BEVERAGES,
	}

	public static MenuCategory[] categories = {
		MenuCategory.MENU_CAT_APPETIZERS,
		MenuCategory.MENU_CAT_DESSERTS,
		MenuCategory.MENU_CAT_GRILL,
		MenuCategory.MENU_CAT_PASTA,
		MenuCategory.MENU_CAT_SALADS,
		MenuCategory.MENU_CAT_SANDWICHES,
		MenuCategory.MENU_CAT_BURGERS,
		MenuCategory.MENU_CAT_BEVERAGES,
	};
	
	public static List<ModelOrder> getOrderType() {
		// ≤Àµ•¿‡–Õ
		List<ModelOrder> orderTypes = new ArrayList<ModelOrder>();
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_APPETIZERS,
				"Appetizers", 0, null, R.drawable.big_party_platter_appetizer,
				0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_DESSERTS,
				"Desserts", 0, null, R.drawable.banana_special_pizza, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_GRILL, "Grill", 0,
				null, R.drawable.coulotte_steak_picanha, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_PASTA, "Pasta", 0,
				null, R.drawable.al_pesto_pasta, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_SALADS, "Salads",
				0, null, R.drawable.andrea_s_favorite_salad, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_SANDWICHES,
				"Sandwiches", 0, null, R.drawable.bauru_sandwich, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_BURGERS, "Burgers",
				0, null, R.drawable.bbq_bacon_burger, 0));
		orderTypes.add(new ModelOrder(MenuCategory.MENU_CAT_BEVERAGES,
				"Beverages", 0, null, R.drawable._7_up, 0));
		return orderTypes;
	}
	
	public static HashMap<MenuCategory, List<ModelOrder>> getOrders() {
		HashMap<MenuCategory, List<ModelOrder>> orders = new HashMap<MenuCategory, List<ModelOrder>>();
		List<ModelOrder> ordersAppetizers = new ArrayList<ModelOrder>();
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Big Party Platter Appetizer",
						36.0,
						"Try a sample of our homemade appetizers: six Brazilian cheese breads, one croquette de camarao, one coxinha eight kibes, eight bolinhos debacalhau and fried yucca.",
						R.drawable.big_party_platter_appetizer, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Bolinho de Bacalhau",
						8.75,
						"A Brazilian speciality - 8 cod fish croquettes fried in vegetable oil.",
						R.drawable.bolinho_de_bacalhau, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Caprese",
						7.75,
						"Marinated fresh mozzarella, marinated tomatoes, basil and olive oil served with fresh sliced bread.",
						R.drawable.caprese, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Charbroiled Shrimp",
						9.0,
						"Garlic charbroiled shrimp served with yucca flour, salsa and fresh sliced bread.",
						R.drawable.charbroiled_shrimp, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Chicken Brochette",
						9.00,
						"Fried chunks of boneless chicken breast wrapped in bacon, on a bed of gorgonzola cream sauce. Served with a side of fresh sliced bread. A must try! Please be aware of the toothpicks!",
						R.drawable.chicken_brochette, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Chicken Fingers",
						7.75,
						"Homemade golden brown breaded chicken tenders served with a homemade hot sauce or a mild lemon cream sauce or blue cheese dressing. For all three add $1.29.",
						R.drawable.chicken_fingers, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Coxinha",
						4.25,
						"Brazilian delicacy made with chicken and cheese inside a fried breaded dough.",
						R.drawable.coxinha, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Croquette de Camarao",
						4.25,
						"Brazilian delicacy made with shrimp and cheese inside a fried breaded dough.",
						R.drawable.croquette_de_camarao, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Deluxe Dutch Fries",
						5.00,
						"Our French fries gratin with our homemade gorgonzola sauce and crumbled gorgonzola cheese served in a cone. Just like in Amsterdam!",
						R.drawable.deluxe_dutch_fries, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Fresh Mozzarella Bruschetta",
						8.0,
						"Toasted fresh sliced bread topped with tomatoes, garlic, basil and fresh mozzarella in our homemade balsamic dressing.",
						R.drawable.fresh_mozzarella_bruschetta, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Fried Yucca",
						7.0,
						"Chunks of yucca root, deep fried and topped with parmesan cheese.",
						R.drawable.fried_yucca, 0));
		ordersAppetizers.add(new ModelOrder(MenuCategory.MENU_CAT_APPETIZERS,
				"Garlic Bread", 4.25, "Garlic bread", R.drawable.garlic_bread,
				0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Kibe",
						7.75,
						"A Brazilian-Arabian delicacy made up of 8 pieces of fried bulghar wheat mixed with ground beef and herbs.",
						R.drawable.kibe, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Linguica Frita with Yucca",
						12.0,
						"Sauteed Brazilian style sausages with onions and yucca. Served with yucca flour and salsa. Have fresh sliced bread instead of yucca at no extra cost.",
						R.drawable.linguica_frita_with_yucca, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Mushrooms Appetizer",
						7.0,
						"Fresh mushrooms sauteed in olive oil, garlic, parmesan and sherry sauce. Served with fresh sliced bread.",
						R.drawable.mushrooms_appetizer, 0));
		ordersAppetizers.add(new ModelOrder(MenuCategory.MENU_CAT_APPETIZERS,
				"Pao de Queijo", 4.00, "Delicious Brazilian cheese breads.",
				R.drawable.pao_de_queijo, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Picanha Appetizer",
						17.00,
						"Charbroiled aged Brazilian prime cut coulotte steak topped with grilled onions. Served with yucca flour, salsa and fresh sliced bread.",
						R.drawable.picanha_appetizer, 0));
		ordersAppetizers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_APPETIZERS,
						"Small Sample Platter Appetizer",
						20.0,
						"Try a sample of our homemade appetizers: fried yucca, six Brazilian cheese breads, one coxinha and one croquette de camarao.",
						R.drawable.small_sample_platter_appetizer, 0));
		orders.put(MenuCategory.MENU_CAT_APPETIZERS, ordersAppetizers);

		List<ModelOrder> ordersDesserts = new ArrayList<ModelOrder>();
		ordersDesserts
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_DESSERTS,
						"Banana Special Pizza",
						10.1,
						"Fresh bananas, mozzarella with sugar and cinnamon mix. A La Mode (with vanilla ice-cream) add $2.69.",
						R.drawable.banana_special_pizza, 0));
		ordersDesserts
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_DESSERTS,
						"Caramelized Bananas",
						5.1,
						"Warm caramelized bananas, vanilla ice-cream, topped with whipped cream and roasted almonds.",
						R.drawable.caramelized_bananas, 0));
		ordersDesserts.add(new ModelOrder(MenuCategory.MENU_CAT_DESSERTS,
				"Colossal Caramel Fudge Cheese Cake", 6.1, "Almost 4\" tall.",
				R.drawable.colossal_caramel_fudge_cheese_cake, 0));
		ordersDesserts
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_DESSERTS,
						"Colossal Chocolate Grande Cake",
						9.1,
						"More than 14 oz per slice. Served warmed and with vanilla ice cream.",
						R.drawable.colossal_chocolate_grande_cake, 0));
		ordersDesserts.add(new ModelOrder(MenuCategory.MENU_CAT_DESSERTS,
				"Homemade Passion Fruit Mousse", 6.1, "",
				R.drawable.homemade_passion_fruit_mousse, 0));
		ordersDesserts.add(new ModelOrder(MenuCategory.MENU_CAT_DESSERTS,
				"Ivone's Pudim de Leite", 4.51, "Homemade milk flan.",
				R.drawable.ivone_s_pudim_de_leite, 0));
		ordersDesserts.add(new ModelOrder(MenuCategory.MENU_CAT_DESSERTS,
				"Vanilla Ice-Cream (Two Scoops)", 3.51, "",
				R.drawable.vanilla_ice_cream_two_scoops, 0));
		ordersDesserts
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_DESSERTS,
						"Yoram's Special",
						6.01,
						"Fried plantains, vanilla ice-cream, chocolate syrup and homemade caramel sauce with whipped cream on top.",
						R.drawable.yoram_s_special, 0));
		orders.put(MenuCategory.MENU_CAT_DESSERTS, ordersDesserts);

		List<ModelOrder> ordersGrill = new ArrayList<ModelOrder>();
		ordersGrill
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_GRILL,
						"Coulotte Steak Picanha",
						18.0,
						"Grilled coulotte steak served with white rice, black beans, salsa and yucca flour. Your choice of French fries or fried plantains.",
						R.drawable.coulotte_steak_picanha, 0));
		ordersGrill
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_GRILL,
						"Rib Eye Steak",
						22.0,
						"Rib eye steak served with white rice, black beans, salsa and yucca flour. Your choice of French fries or fried plantains.",
						R.drawable.rib_eye_steak, 0));
		ordersGrill
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_GRILL,
						"Sauteed or Steamed Vegetable Platter (Seasonal Vegetables)",
						11.0,
						"Seasonal vegetables served with white rice, black beans, salsa and yucca flour.",
						R.drawable.sauteed_or_steamed_vegetable_platter_seasonal_vegetables,
						0));
		ordersGrill.add(new ModelOrder(MenuCategory.MENU_CAT_GRILL,
				"Seabass Fillet", 14.0, "", R.drawable.seabass_fillet, 0));
		ordersGrill.add(new ModelOrder(MenuCategory.MENU_CAT_GRILL,
				"Skirt Steak", 18.0,
				"Served with White Rice, Black Beans, Salsa and Yucca Flour.",
				R.drawable.skirt_steak, 0));
		ordersGrill
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_GRILL,
						"The Traditional Fresh Boneless Chicken Breast",
						12.0,
						"The traditional fresh boneless chicken breast served with white rice, black beans, salsa and yucca flour. Your choice of French fries or fried plantains.",
						R.drawable.the_traditional_fresh_boneless_chicken_breast,
						0));
		orders.put(MenuCategory.MENU_CAT_GRILL, ordersGrill);

		List<ModelOrder> ordersPasta = new ArrayList<ModelOrder>();
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Al Pesto Pasta",
						11.0,
						"Any noodle of your choice with our delicious homemade pesto cream sauce, sundried tomatoes and fresh sauteed boneless chicken breast. Suggested noodle: fettuccini or penne.",
						R.drawable.al_pesto_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Alfredo Pasta",
						9.75,
						"The classic! Grated parmesan cheese in a cream sauce. Suggested noodle: fettuccine.",
						R.drawable.alfredo_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Alioleo Pasta",
						9.50,
						"Olive oil, garlic and parmesan. Suggested noodle: spaghetti. With fresh basil and fresh tomatoes add $2.29. Suggested noodle: spaghetti.",
						R.drawable.alioleo_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Arrabiata Pasta (Hot! Hot! Hot!)",
						10.75,
						"Sliced bacon, onions, parmesan, garlic, olive oil, parsley and hot crushed red pepper in a spicy tomato sauce. Suggested noodle: penne or spaghetti.",
						R.drawable.arrabiata_pasta_hot_hot_hot, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Bossa Bolognesa Pasta",
						10.0,
						"Bossa Nova version of the traditional Italian meat sauce with a touch of a cream and pancetta (Italian bacon) suggested noodle: spaghetti.",
						R.drawable.bossa_bolognesa_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Carbonara Pasta",
						11.00,
						"The classic! With pancetta, very light white homemade sauce, garlic, parmesan, parsley, egg yolk and seasoning. Suggested noodle: spaghetti.",
						R.drawable.carbonara_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Chicken Lasagna",
						13.0,
						"Homemade lasagna with shredded boneless chicken breast, spinach, homemade tomato and white cream sauces with layers of monterey jack cheese. It's fantastic!",
						R.drawable.chicken_lasagna, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Giovanna's Pasta",
						12.0,
						"Chopped prosiutto, boneless chicken breast, asparagus, sauteed onions in a homemade white cream sauce. Suggested noodle: penne or fettuccine.",
						R.drawable.giovanna_s_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Lasagna",
						13.00,
						"The original homemade bolognese sauce made with X-lean ground beef, ham and pancetta topped and baked with white cream sauce and parmesan.",
						R.drawable.lasagna, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Manuella's Special Pasta",
						13.75,
						"Shrimp, chicken breast, prosiutto, onions with our own cajun cream sauce topped and gratin with parmesan. Suggested noodle: penne or spaghetti.",
						R.drawable.manuella_s_special_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Rafael's Pasta",
						12.0,
						"Chopped salami, bacon, fontina cheese, sundried tomatoes, garlic and onions in a spicy white cream sauce. Suggested noodle: fettuccine or penne.",
						R.drawable.rafael_s_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Rosada Pasta",
						10.0,
						"A special homemade pink sauce made with cream, tomato sauce and basil. A must try! Suggested noodle: gnocchi or penne.",
						R.drawable.rosada_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Seafood Pasta",
						15.0,
						"Seasoning sauteed shrimps, smoked salmon, fresh salmon, capers, asparagus in a cream sauce. Suggested noodle: penne or fettuccine.",
						R.drawable.seafood_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Sunset Pasta",
						12.0,
						"Asparagus, boneless chicken breast, onions and mushrooms in a cream sauce. Suggested noodle: fettuccine or penne.",
						R.drawable.sunset_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Tiriri's Pasta",
						12.0,
						"Sundried tomatoes, herbs, onions, fresh boneless chicken breast in a white wine cream sauce. Suggested noodle: penne or fettuccine.",
						R.drawable.tiriri_s_pasta, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Vegetable Lasagna",
						13.0,
						"Homemade lasagna with vegetables, spinach, monterey jack, parmesan, spinach or goat cheese, cream sauce topped and baked with a parmesan.",
						R.drawable.vegetable_lasagna, 0));
		ordersPasta
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_PASTA,
						"Veggie Pasta",
						10.0,
						"Sauteed fresh vegetables in olive oil with our homemade marinara sauce. Suggested Noodle: Fettuccine or penne.",
						R.drawable.veggie_pasta, 0));
		orders.put(MenuCategory.MENU_CAT_PASTA, ordersPasta);

		List<ModelOrder> ordersSalads = new ArrayList<ModelOrder>();
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Andrea's Favorite Salad",
						7.0,
						"Romaine lettuce, walnuts, marinated sundried tomatoes and gorgonzola cheese, tossed in honey mustard dressing with grilled chicken breast on top.",
						R.drawable.andrea_s_favorite_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Avery's Salad",
						7,
						"Mixed greens, caramelized pears, crumbled walnuts and homemade honey mustard dressing.",
						R.drawable.avery_s_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Bossa's Salad",
						9.0,
						"A wonderful salad with romaine, tomatoes, roasted almonds, gorgonzola, homemade croutons and onions, tossed in our homemade balsamic dressing.",
						R.drawable.bossa_s_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Caesar Salad",
						6.5,
						"Fresh romaine lettuce, homemade croutons, tossed with our homemade caesar dressing, topped with fine shaved parmesan.",
						R.drawable.caesar_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Chico's Salad",
						8.50,
						"Mixed greens, tomatoes, black olives, hearts of palm and our homemade balsamic dressing.",
						R.drawable.chico_s_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Edgar's Goat Cheese Seafood Salad",
						13.0,
						"An unique salad with fresh spinach, fresh arugula, onions, sundried tomatoes, goat cheese and walnuts, tossed in a light homemade dijon dressing served with sauteed scallops, shrimps and shitake mushrooms deglazed with white wine.",
						R.drawable.edgar_s_goat_cheese_seafood_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Greek Salad",
						7.5,
						"Romaine, tomatoes, green and red bell peppers, onions, kalamata olives, cucumbers and feta cheese in our homemade Greek dressing.",
						R.drawable.greek_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Isabel's Salad",
						14.0,
						"Mixed greens tossed with honey mustard dressing, topped with roasted bell peppers and skirt steak.",
						R.drawable.isabel_s_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Mama's Salad (Full)",
						12.0,
						"Mixed greens, smashed cod fish croquette, caramelized pears and walnuts with honey mustard dressing.",
						R.drawable.mama_s_salad_full, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Roman's Special BBQ Salad",
						8.50,
						"Oven roasted chilled BBQ chicken, romaine lettuce, onions, roasted walnuts, gouda cheese, tomatoes, roasted red peppers, cilantro and corn tossed in our homemade BBQ sauce.",
						R.drawable.roman_s_special_bbq_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Santa Fe Salad",
						12.0,
						"A flour tortilla shell stuffed with our famous black beans, white rice, boneless chicken breast, romaine lettuce, tomatoes, corn, monterey jack, cilantro and sour cream.",
						R.drawable.santa_fe_salad, 0));
		ordersSalads
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SALADS,
						"Warm Spinach Salad",
						12.0,
						"Fresh spinach and fresh arugula tossed with sauteed green and red bell peppers, shitake mushrooms, onions, sundried tomatoes and boneless chicken breast deglazed in marsala wine.",
						R.drawable.warm_spinach_salad, 0));
		orders.put(MenuCategory.MENU_CAT_SALADS, ordersSalads);

		List<ModelOrder> ordersSandwiches = new ArrayList<ModelOrder>();
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Bauru Sandwich",
						9.75,
						"Brazilian grilled sandwich with ham, melted monterey jack, fried eggs, lettuce, tomatoes and mayo on toasted Italian bread.",
						R.drawable.bauru_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"BBQ Pulled Pork Sandwich",
						11.0,
						"Slow roasted sliced pulled pork with our homemade BBQ sauce, fontina cheese, onions on a hamburger bun.",
						R.drawable.bbq_pulled_pork_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Bossa Nova Sandwich",
						10.75,
						"Boneless chicken breast with melted monterey jack, lettuce, mayo and our own salsa on toasted Italian bread.",
						R.drawable.bossa_bolognesa_pasta, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Cajun Chicken Breast Sandwich",
						11.00,
						"Charbroiled blackened chicken breast, bacon, onions, monterey jack and sundried pesto spread on toasted Italian bread.",
						R.drawable.cajun_chicken_breast_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Carla's Vegetarian Special Sandwich",
						9.75,
						"Marinated charbroiled vegetables, sundried tomatoes and a homemade goat cheese mix spread on toasted Italian bread.",
						R.drawable.carla_s_vegetarian_special_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Chucho's Chicken Club Sandwich",
						11.0,
						"Charbroiled marsala marinated boneless chicken breast, with melted monterey jack, marinated tomatoes, lettuce, homemade green mayo with bacon on toasted Italian bread.",
						R.drawable.chucho_s_chicken_club_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Copacabana Sandwich",
						10.75,
						"Philly style, with thin cut sirloin steak, mid-west corn fed beef, aged 28 days. Melted monterey jack, lettuce, chopped tomatoes, roasted red bell peppers, cherry peppers, grilled onions and mayo on toasted Italian bread.",
						R.drawable.copacabana_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Fillet com Abacaxi (Fillet Mignon) Sandwich",
						11.75,
						"Grilled tender loin fillet with pineapple, caramelized onions, arugula and monterey jack on a hamburger bun. Another Rio de Janeiro local favorite for after hours snack!",
						R.drawable.fillet_com_abacaxi_fillet_mignon_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Havana Sandwich",
						9.75,
						"Caramelized bananas, melted monterey jack, mascarpone cheese, with a touch of cinnamon stuffed in a toasted Italian bread.",
						R.drawable.havana_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Lambada Sandwich",
						11.75,
						"Lightly fried breaded boneless chicken breast, with marinated fresh mozzarella, romaine lettuce, marinated tomatoes and our special green mayo on toasted Italian bread.",
						R.drawable.lambada_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Linguica No Pao Sausage Sandwich",
						11.0,
						"	Three Brazilian style with melted monterey jack, lettuce, mayo and our own salsa on toasted Italian bread.",
						R.drawable.linguica_no_pao_sausage_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Lombinho com Abacaxi (Pulled Pork) Sandwich",
						11.0,
						"Slow roasted sliced pulled pork Brazilian style with pineapple, monterey jack and caramelized onions on a hamburger bun. A Rio de Janeiro local favorite for after hours snack!",
						R.drawable.lombinho_com_aabacaxi_pulled_pork_sandwich,
						0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Tuna Roll Sandwich",
						9.75,
						"Special blend of albacore tuna, chopped tomatoes, onions, mayo and melted monterey jack folded in a tortilla with lettuce and tomatoes.",
						R.drawable.tuna_roll_sandwich, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Willy's Favorite Burrito",
						11.0,
						"A burrito prepared with our famous black beans, white rice, boneless chicken breast, monterey jack, salsa and sour cream.",
						R.drawable.willy_s_favorite_burrito, 0));
		ordersSandwiches
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_SANDWICHES,
						"Wrapped Chicken Caesar Sandwich",
						10.0,
						"Caesar salad with our charbroiled marsala boneless chicken breast wrapped in a tortilla.",
						R.drawable.wrapped_chicken_caesar_sandwich, 0));
		orders.put(MenuCategory.MENU_CAT_SANDWICHES, ordersSandwiches);

		List<ModelOrder> ordersBurgers = new ArrayList<ModelOrder>();
		ordersBurgers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_BURGERS,
						"BBQ Bacon Burger",
						10.0,
						"A \"huge\" open face charbroiled burger with a whopping half pound patty, monterey jack, marinated mushrooms, homemade roasted garlic mayo, lettuce, tomatoes and onions on a hamburger bun.",
						R.drawable.bbq_bacon_burger, 0));
		ordersBurgers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_BURGERS,
						"Bossa Burger",
						10.0,
						"Charbroiled half pound patty, monterey jack, bacon, onions, lettuce and our homemade BBQ sauce on a hamburger bun.",
						R.drawable.bossa_burger, 0));
		ordersBurgers
				.add(new ModelOrder(
						MenuCategory.MENU_CAT_BURGERS,
						"Gorgonzola Burger",
						10.0,
						"A \"huge\" open face burger with a whopping half pound patty, gorgonzola cheese, lettuce, tomatoes and onions on a hamburger bun.",
						R.drawable.gorgonzola_burger, 0));
		orders.put(MenuCategory.MENU_CAT_BURGERS, ordersBurgers);

		List<ModelOrder> ordersBeverages = new ArrayList<ModelOrder>();
		ordersBeverages.add(new ModelOrder(MenuCategory.MENU_CAT_BEVERAGES,
				"7 Up", 2.0, "250ml", R.drawable._7_up, 0));
		ordersBeverages.add(new ModelOrder(MenuCategory.MENU_CAT_BEVERAGES,
				"Mirinda", 3.0, "250ml", R.drawable.mirinda, 0));
		ordersBeverages.add(new ModelOrder(MenuCategory.MENU_CAT_BEVERAGES,
				"Pepsi", 2.5, "250ml", R.drawable.pepsi, 0));
		orders.put(MenuCategory.MENU_CAT_BEVERAGES, ordersBeverages);
		return orders;
	}
	

}
