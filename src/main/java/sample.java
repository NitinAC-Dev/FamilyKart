public class sample {
    public static void main(String[] args) {




            int startId = 601; // Starting ID
            int endId = 1000; // Ending ID
            String baseCategoryName = "Category ";

            for (int i = startId; i <= endId; i++) {
                System.out.println("INSERT INTO CATEGORY_TABLE (CATEGORYID, CATEGORY_NAME) VALUES (" + i + ", '" + baseCategoryName + i + "');");
            }
    }
}
