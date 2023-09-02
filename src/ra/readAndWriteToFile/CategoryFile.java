package ra.readAndWriteToFile;

import ra.entity.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ra.run.Store.listCategory;

public class CategoryFile {
    public static void writeCategoryDataToFile(List<Category> listCategory) {
        //1. Khởi tạo đối tượng file để làm việc với file - tương đối
        File file = new File("categories.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //2. Khởi tạo đối tượng FileOutputStream từ file - Checked Excetion
            fos = new FileOutputStream(file);
            //3. Khởi tạo đối tượng ObjectOutputStream từ fos
            oos = new ObjectOutputStream(fos);
            //4. Sử dụng writeObject để ghi object ra file
            oos.writeObject(listCategory);
            //5. Đẩy dữ liệu từ Stream xuống file
            oos.flush();

        } catch (FileNotFoundException ex1) {
            System.err.println("File không tồn tại");
        } catch (IOException ex2) {
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        } catch (Exception ex) {
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        } finally {
            //6. Đóng các stream
            closeStream(fos);
            closeStream(oos);
        }
    }

    public static List<Category> readCategoryDataFromFile() {
        //1. Khởi tạo đối tượng File
        File file = new File("categories.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            //2. Khởi tạo đối tượng FileInputStream
            fis = new FileInputStream(file);
            //3. Khởi tạo đối tượng ObjectInputStream
            ois = new ObjectInputStream(fis);
            //4. Đọc dữ liệu object từ file (readObject())
            listCategory = (List<Category>) ois.readObject();
        } catch (FileNotFoundException ex1) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex2) {
            System.err.println("Lỗi khi đọc file");
//            System.out.println(ex2.getMessage());
        } catch (Exception ex) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            //6. Đóng các stream
            closeStream(fis);
            closeStream(ois);
        }
        return listCategory;
    }

    private static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
