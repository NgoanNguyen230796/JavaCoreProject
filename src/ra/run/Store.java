package ra.run;

import ra.color.Colors;
import ra.entity.Product;
import ra.readAndWriteToFile.CategoryFile;
import ra.entity.Category;
import ra.readAndWriteToFile.ProductFile;

import java.util.*;
import java.util.stream.Collectors;

import static ra.entity.Category.isCheckFindCategoryId;
import static ra.entity.Category.isCheckFindIndex;
import static ra.entity.Product.indexDataProductIdFind;
import static ra.entity.Product.isCheckCatalogIdInListProduct;

public class Store {
    public static Scanner sc = new Scanner(System.in);
    public static List<Category> listCategory = new ArrayList<>();
    public static List<Product> listProduct = new ArrayList<>();
    public static final String border = "-";

    public static void main(String[] args) {
        //Đọc dữ liệu từ file categories.txt và file products.txt
        listCategory = CategoryFile.readCategoryDataFromFile();
        listProduct = ProductFile.readProductDataFromFile();
        do {
            String repeated = new String(new char[67]).replace("\0", border);
            System.out.println(Colors.YELLOW_BOLD + repeated);
            System.out.println("=========================== QUẢN LÝ KHO ===========================");
            System.out.println("--              1. Quản lý danh mục                              --");
            System.out.println("--              2. Quản lý sản phẩm                              --");
            System.out.println("--              3. Thoát                                         --");
            System.out.println(repeated + Colors.ANSI_RESET);
            System.out.print("Lựa chọn của bạn là :");
            int choiceMain = validateChoiceMain();
            switch (choiceMain) {
                case 1:
                    Store.categoryMenu();
                    break;
                case 2:
                    Store.productMenu();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Vui lòng nhập lựa chọn của bạn từ 1-3");
            }
        } while (true);
    }

    public static int validateChoiceMain() {
        while (true) {
            String inputChoiceMain = sc.nextLine().trim();
            if (inputChoiceMain.length() == 0) {
                System.err.println("Lựa chọn các mục trong QUẢN LÝ KHO không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int choiceMain = Integer.parseInt(inputChoiceMain);
                    if (choiceMain > 0) {
                        return choiceMain;
                    } else {
                        System.err.println("Lựa chọn các mục trong QUẢN LÝ KHO phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Lựa chọn các mục trong QUẢN LÝ KHO phải là số,vui lòng nhập lại");
                }
            }
        }
    }

    public static void categoryMenu() {
        boolean isExitCatalogMenu = true;
        do {
            String repeated = new String(new char[67]).replace("\0", border);
            System.out.println(Colors.GREEN_BOLD_BRIGHT + repeated);
            System.out.println("========================= QUẢN LÝ DANH MỤC ========================");
            System.out.println("--              1. Thêm mới danh mục                             --");
            System.out.println("--              2. Cập nhật danh mục                             --");
            System.out.println("--              3. Xóa danh mục                                  --");
            System.out.println("--              4. Tìm kiếm danh mục theo tên danh mục           --");
            System.out.println("--              5. Thống kê số lượng sp đang có trong danh mục   --");
            System.out.println("--              6. Hiển thị danh mục                             --");
            System.out.println("--              7. Quay lại(Quay lại QUẢN LÝ KHO)                --");
            System.out.println(repeated + Colors.ANSI_RESET);
            System.out.print("Lựa chọn của bạn là :");
            int choiceCategoryMenu = validateChoiceCategoryMenu();
            switch (choiceCategoryMenu) {
                case 1:
                    Store.inputDataCategoryMenu();
                    break;
                case 2:
                    Store.displayDataCategoryMenu();
                    Store.updateDataCategoryMenuByCategoryId();
                    break;
                case 3:
                    Store.displayDataCategoryMenu();
                    Store.deleteDataCategoryMenuByCategoryId();
                    break;
                case 4:
                    Store.displayDataCategoryMenu();
                    Store.searchCategoryByCategoryName();
                    break;
                case 5:
                    Store.groupProductsByCatalogId();
                    break;
                case 6:
                    Store.displayDataCategoryMenu();
                    break;
                case 7:
                    isExitCatalogMenu = false;
                    break;
                default:
                    System.out.println("Vui lòng nhập lựa chọn của bạn từ 1-7");
            }
        } while (isExitCatalogMenu);
    }

    public static int validateChoiceCategoryMenu() {
        while (true) {
            String inputChoiceCategoryMenuStr = sc.nextLine().trim();
            if (inputChoiceCategoryMenuStr.length() == 0) {
                System.err.println("Lựa chọn các mục trong QUẢN LÝ DANH MỤC không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int choiceCategoryMenu = Integer.parseInt(inputChoiceCategoryMenuStr);
                    if (choiceCategoryMenu > 0) {
                        return choiceCategoryMenu;
                    } else {
                        System.err.println("Lựa chọn các mục trong QUẢN LÝ DANH MỤC phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Lựa chọn các mục trong QUẢN LÝ DANH MỤC phải là số,vui lòng nhập lại");
                }
            }
        }
    }

    public static void productMenu() {
        boolean isExitProductMenu = true;
        do {
            String repeated = new String(new char[67]).replace("\0", border);
            System.out.println(Colors.PURPLE_BOLD + repeated);
            System.out.println("========================= QUẢN LÝ SẢN PHẨM ========================");
            System.out.println("--              1. Thêm mới sản phẩm                             --");
            System.out.println("--              2. Cập nhật sản phẩm                             --");
            System.out.println("--              3. Xóa sản phẩm                                  --");
            System.out.println("--              4. Hiển thị sản phẩm                             --");
            System.out.println("--              5. Hiển thị sản phẩm theo tên A-Z                --");
            System.out.println("--              6. Hiển thị sản phẩm theo lợi nhuận từ cao-thấp  --");
            System.out.println("--              7. Tìm kiếm sản phẩm                             --");
            System.out.println("--              8. Quay lại(Quay lại QUẢN LÝ KHO)                --");
            System.out.println(repeated + Colors.ANSI_RESET);
            System.out.print("Lựa chọn của bạn là :");
            int choiceProductMenu = validateChoiceProductMenu();
            switch (choiceProductMenu) {
                case 1:
                    Store.inputDataProductMenu();
                    break;
                case 2:
                    Store.displayDataProductMenuByProductName();
                    Store.updateDataProductMenuByProductId();
                    break;
                case 3:
                    Store.displayDataProductMenuByProductName();
                    Store.deleteDataProductMenuByProductId();
                    break;
                case 4:
                    System.out.println("Hiển thị thông tin sản phẩm");
                    Store.displayDataProductMenuByProductName();
                    break;
                case 5:
                    Store.displayDataProductMenuByProductNameByAZ();
                    Store.displayDataProductMenuByProductName();
                    break;
                case 6:
                    Store.displayDataProductMenuByProfitByHighToLow();
                    Store.displayDataProductMenuByProductName();
                    break;
                case 7:
                    Store.displayDataProductMenuByProductName();
                    Store.searchDataProductMenuByProductNamOrExportPriceOrimportPrice();
                    break;
                case 8:
                    isExitProductMenu = false;
                    break;
                default:
                    System.out.println("Vui lòng nhập lựa chọn của bạn từ 1-8");
            }
        } while (isExitProductMenu);
    }

    public static int validateChoiceProductMenu() {
        while (true) {
            String inputChoiceProductMenuStr = sc.nextLine().trim();
            if (inputChoiceProductMenuStr.length() == 0) {
                System.err.println("Lựa chọn các mục trong QUẢN LÝ SẢN PHẨM không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int choiceProductMenu = Integer.parseInt(inputChoiceProductMenuStr);
                    if (choiceProductMenu > 0) {
                        return choiceProductMenu;
                    } else {
                        System.err.println("Lựa chọn các mục trong QUẢN LÝ SẢN PHẨM phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Lựa chọn các mục trong QUẢN LÝ SẢN PHẨM phải là số,vui lòng nhập lại");
                }
            }
        }
    }

    //1.Thêm mới danh mục
    public static void inputDataCategoryMenu() {
        System.out.print("Nhập số lượng danh mục mà bạn muốn thêm :");

        while (true) {
            String inputNumberStr = sc.nextLine().trim();
            if (inputNumberStr.length() == 0) {
                System.err.println("Số lượng danh mục mà bạn muốn thêm không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int number = Integer.parseInt(inputNumberStr);
                    if (number > 0) {
                        for (int i = 0; i < number; i++) {
                            System.out.println("Nhập vào danh mục thứ " + (i + 1) + ":");
                            Category categories = new Category();
                            categories.inputData();
                            listCategory.add(categories);
                            CategoryFile.writeCategoryDataToFile(listCategory);
                        }
                        System.out.println("Đã thêm mới danh mục sản phẩm xong");
                        break;
                    } else {
                        System.err.println("Số lượng danh mục mà bạn muốn thêm phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }

                } catch (NumberFormatException ex) {
                    System.err.println("Số lượng danh mục mà bạn muốn thêm phải là số,vui lòng nhập lại");
                }
            }

        }
    }

    public static void displayDataCategoryMenu() {
//        List<Category> listCategoryRead = CategoryFile.readCategoryDataFromFile();
        String repeated = new String(new char[123]).replace("\0", border);
        System.out.println("* " + repeated + " *");
        System.out.printf("| %-15s | %-40s | %-40s | %-20s|\n", "Mã danh mục", "Tên danh mục", "Mô tả danh mục", "Trạng thái");
        System.out.println("* =========================================================================================================================== *");
        for (Category ca : listCategory) {
            ca.displayData();
        }
        System.out.println("* " + repeated + " *");

    }

    //2.Cập nhật danh mục
    public static void updateDataCategoryMenuByCategoryId() {
        System.out.print("Nhập vào mã danh mục mà bạn muốn cập nhật thông tin =");
        int index = isCheckFindCategoryId();
        if (index >= 0) {
            System.out.print("Bạn muốn thay đổi tên danh mục" + " từ " + "[" + listCategory.get(index).getCategoryName() + "]" + " thành : ");
//            listCategory.get(index).setCategoryName(Category.inputCategoryName());
            listCategory.get(index).setCategoryName(Category.inputUpdateCategoryName(index));
            System.out.print("Bạn muốn thay đổi  mô tả danh mục" + " từ " + "[" + listCategory.get(index).getCategoryDescription() + "]" + " thành : ");
            listCategory.get(index).setCategoryDescription(Category.inputCategoryDescription());
            System.out.print("Bạn muốn thay đổi trạng thái danh mục" + " từ " + "[" + listCategory.get(index).getCategoryStatus() + "]" + " thành : ");
            listCategory.get(index).setCategoryStatus(Category.inputCategoryStatus());
            CategoryFile.writeCategoryDataToFile(listCategory);
            System.out.println("Đã cập nhật xong thông tin");
        } else {
            System.out.println("Không tìm thấy thông tin với mã danh mục mà bạn vừa nhập");
        }
    }

    //3.Cập nhật danh mục
    public static void deleteDataCategoryMenuByCategoryId() {
        System.out.print("Nhập vào mã danh mục mà bạn muốn xóa thông tin =");
        while (true) {
            String inputCatalogIdFindStr = sc.nextLine().trim();
            if (inputCatalogIdFindStr.length() == 0) {
                System.err.println("Mã danh mục không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int catalogIdFind = Integer.parseInt(inputCatalogIdFindStr);
                    if (catalogIdFind > 0) {
                        int isCheckFind = isCheckFindIndex(catalogIdFind);
                        if (isCheckFind < 0) {
                            System.out.println("Không tìm thấy mã danh mục : " + catalogIdFind + " mà bạn muốn xóa");
                        } else {
                            boolean isCheckCatalogIdByCatalogIdInProduct = isCheckCatalogIdInListProduct(catalogIdFind);
                            if (!isCheckCatalogIdByCatalogIdInProduct) {
                                listCategory.remove(isCheckFind);
                                CategoryFile.writeCategoryDataToFile(listCategory);
                                System.out.println("Đã xóa xong danh mục với mã danh mục là:" + catalogIdFind);
                            } else {
                                System.out.println("Rất tiếc mã danh mục " + catalogIdFind + " có chứa sản phẩm,không thể xóa danh mục này");
                            }
                        }
                        break;
                    } else {
                        System.err.println("Mã danh mục bắt buộc phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Mã danh mục bắt buộc phải là số,vui lòng nhập lại");
                }

            }
        }

    }

    //4.Tìm kiếm danh mục theo tên danh mục
    public static void searchCategoryByCategoryName() {
        System.out.print("Nhập vào tên danh mục mà bạn muốn tìm kiếm = ");
        while (true) {
            String searchDataByCategory = sc.nextLine().trim();
            if (searchDataByCategory.length() == 0) {
                System.err.println("Tên danh mục mà bạn muốn tìm kiếm không được để trống,vui lòng nhập lại");
            } else {
                List<Category> listCategoryFilter = listCategory.stream().filter(category -> category.getCategoryName().toLowerCase().contains(searchDataByCategory.toLowerCase())).collect(Collectors.toList());
                if (listCategoryFilter.size() == 0) {
                    System.out.println("Không tìm thấy kết quả");
                } else {
                    String repeated = new String(new char[123]).replace("\0", border);
                    System.out.println("* " + repeated + " *");
                    System.out.printf("| %-15s | %-40s | %-40s | %-20s|\n", "Mã danh mục", "Tên danh mục", "Mô tả danh mục", "Trạng thái");
                    System.out.println("* =========================================================================================================================== *");
                    listCategoryFilter.forEach(Category::displayData);
                    System.out.println("* " + repeated + " *");
                }
                break;
            }
        }
    }

    //5.Thống kê số lượng sp đang có trong danh mục
    public static void groupProductsByCatalogId() {
        System.out.println("Thống kê số lượng sản phẩm theo danh mục sản phẩm mà sản phẩm thuộc về là :");
        String repeated = new String(new char[59]).replace("\0", border);
        System.out.println("* " + repeated + " *");
        System.out.printf("| %-30s | %-26s |\n", "Tên danh mục", "Số lượng sản phẩm");
        System.out.println("* =========================================================== *");
        for (Category ca : listCategory) {
            int count = 0;
            for (Product pr : listProduct) {
                if (ca.getCategoryId() == pr.getCategoryId()) {
                    count++;
                }
            }
            System.out.printf("| %-30s | %-26d |\n", listCategory.get(isCheckFindIndex(ca.getCategoryId())).getCategoryName(), count);
            System.out.println("* " + repeated + " *");
        }

    }

    //1. Thêm mới sản phẩm
    public static void inputDataProductMenu() {
        System.out.print("Nhập số lượng sản phẩm mà bạn muốn thêm :");
        while (true) {
            String inputNumberStr = sc.nextLine().trim();
            if (inputNumberStr.length() == 0) {
                System.err.println("Số lượng sản phẩm mà bạn muốn thêm không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int number = Integer.parseInt(inputNumberStr);
                    if (number > 0) {
                        for (int i = 0; i < number; i++) {
                            System.out.println("Nhập vào sản phẩm thứ " + (i + 1) + ":");
                            Product product = new Product();
                            product.inputData();
                            //2.Cho chọn sản phẩm thuộc danh mục nào-Hiển thị danh mục để cho người dùng chọn
                            String repeated = new String(new char[20]).replace("\0", border);
                            System.out.println("*" + repeated + "Quản lý danh mục Men" + repeated + "*");
                            for (int j = 0; j < listCategory.size(); j++) {
                                System.out.println((j + 1) + " . " + listCategory.get(j).getCategoryName());
                            }
                            System.out.println("*" + repeated + repeated + repeated + "*");
                            System.out.print("Chọn danh mục mà bạn muốn thêm sản phẩm vào :");
                            while (true) {
                                String inputChoiceNumberStr = sc.nextLine().trim();
                                if (inputChoiceNumberStr.length() == 0) {
                                    System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào không được để trống,vui lòng nhập lại");
                                } else {
                                    try {
                                        int choiceNumber = Integer.parseInt(inputChoiceNumberStr);
                                        if (choiceNumber > listCategory.size()) {
                                            System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải từ 1 đến " + listCategory.size() + ",vui lòng nhập lại");
                                        } else if (choiceNumber > 0) {
                                            product.setCategoryId(listCategory.get(choiceNumber - 1).getCategoryId());
                                            listProduct.add(product);
                                            listProduct.forEach(Product::calProfit);
                                            ProductFile.writeProductDataToFile(listProduct);
                                            break;
                                        } else {
                                            System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                                        }
                                    } catch (NumberFormatException ex) {
                                        System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải là số,vui lòng nhập lại");
                                    }
                                }
                            }

                        }
                        System.out.println("Đã thêm mới sản phẩm xong");
                        break;
                    } else {
                        System.err.println("Số lượng sản phẩm mà bạn muốn thêm phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }

                } catch (NumberFormatException ex) {
                    System.err.println("Số lượng sản phẩm mà bạn muốn thêm phải là số,vui lòng nhập lại");
                }

            }
        }
    }

    public static void displayDataProductMenuByProductName() {
        String repeated = new String(new char[186]).replace("\0", border);
        System.out.println("* " + repeated + " *");
        System.out.printf("| %-15s | %-30s | %-10s | %-10s | %-10s | %-40s | %-20s | %-30s |\n", "Mã sản phẩm", "Tên sản phẩm", "Giá nhập", "Giá bán", "Lợi nhuận", "Mô tả sản phẩm", "Trạng thái", "Tên danh mục thuộc về");
        System.out.println("* ========================================================================================================================================================================================== *");
        for (Product pr : listProduct) {
            pr.displayData();
        }
        System.out.println("* " + repeated + " *");
    }

    //2. Cập nhật sản phẩm
    public static void updateDataProductMenuByProductId() {
        System.out.print("Nhập vào mã sản phẩm mà bạn muốn thay đổi thông tin =");
        while (true) {
            String inputProductId = sc.nextLine().trim();
            if (inputProductId.length() == 0) {
                System.err.println("Mã sản phẩm không được để trống,vui lòng nhập lại");
            } else {
                int isCheckIndexProductId = indexDataProductIdFind(inputProductId);
                if (isCheckIndexProductId < 0) {
                    System.out.println("Không tìm thấy mã sản phẩm: " + inputProductId + " mà bạn muốn cập nhật thông tin");
                } else {
                    //Update tên sản phẩm
                    System.out.print("Bạn muốn thay đổi tên sản phẩm từ " + "[" + listProduct.get(isCheckIndexProductId).getProductName() + "]" + " thành : ");
//                    listProduct.get(isCheckIndexProductId).setProductName(Product.inputProductName());
                    listProduct.get(isCheckIndexProductId).setProductName(Product.inputUpdateProductName(isCheckIndexProductId));
                    //Update giá nhập sản phẩm
                    System.out.print("Bạn muốn thay đổi giá nhập sản phẩm từ " + "[" + listProduct.get(isCheckIndexProductId).getImportPrice() + "]" + " thành : ");
                    listProduct.get(isCheckIndexProductId).setImportPrice(Product.inputImportPrice());
                    //Update giá bán sản phẩm
                    System.out.print("Bạn muốn thay đổi giá bán sản phẩm từ " + "[" + listProduct.get(isCheckIndexProductId).getExportPrice() + "]" + " thành : ");
                    listProduct.get(isCheckIndexProductId).setExportPrice(Product.inputExportPrice(listProduct.get(isCheckIndexProductId).getImportPrice()));
                    //Update mô tả sản phẩm
                    System.out.print("Bạn muốn thay đổi mô tả sản phẩm từ " + "[" + listProduct.get(isCheckIndexProductId).getProductDescription() + "]" + " thành : ");
                    listProduct.get(isCheckIndexProductId).setProductDescription(Product.inputProductDescription());
                    //Update trạng thái sản phẩm
                    System.out.print("Bạn muốn thay đổi trạng thái từ " + "[" + listProduct.get(isCheckIndexProductId).getProductStatus() + "]" + " thành : ");
                    listProduct.get(isCheckIndexProductId).setProductStatus(Product.inputProductStatus());
                    System.out.println("* ―――――――――――――― Quản lý danh mục Menu―――――――――――――― *");
                    for (int j = 0; j < listCategory.size(); j++) {
                        System.out.println((j + 1) + " . " + listCategory.get(j).getCategoryName());
                    }
                    System.out.println("* ―――――――――――――――――――――――――――――――――――――――――― *");
                    System.out.print("Bạn muốn thay đổi danh mục sản phẩm thuộc về từ " + "[" + listCategory.get(isCheckFindIndex(listProduct.get(isCheckIndexProductId).getCategoryId())).getCategoryName() + "]" + " thành : ");
                    while (true) {
                        String choiceNumberStr = sc.nextLine().trim();
                        if (choiceNumberStr.length() != 0) {
                            try {
                                int choiceNumber = Integer.parseInt(choiceNumberStr);
                                if (choiceNumber > listCategory.size()) {
                                    System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải từ 1 đến " + listCategory.size() + ",vui lòng nhập lại");
                                } else if (choiceNumber > 0) {
                                    listProduct.get(isCheckIndexProductId).setCategoryId(listCategory.get(choiceNumber - 1).getCategoryId());
                                    break;
                                } else {
                                    System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                                }
                            } catch (NumberFormatException ex) {
                                System.err.println("Danh mục mà bạn muốn thêm sản phẩm vào phải là số,vui lòng nhập lại");
                            }
                        } else {
                            System.err.println("Danh mục mà bạn muốn thêm sản phẩm không được để trống,vui lòng nhập lại");
                        }

                    }
                    //Tính lợi nhuận sản phẩm
                    listProduct.get(isCheckIndexProductId).calProfit();
                    ProductFile.writeProductDataToFile(listProduct);
                    System.out.println("Đã cập nhật thành công thông tin với mã sản phẩm là " + inputProductId);
                }
                break;
            }
        }


    }

    //3.Xóa sản phẩm
    public static void deleteDataProductMenuByProductId() {
        System.out.print("Nhập vào mã sản phẩm mà bạn muốn xóa thông tin =");
        while (true) {
            String productIdFind = sc.nextLine().trim();
            if (productIdFind.length() == 0) {
                System.err.println("Mã sản phẩm không được để trống,vui lòng nhập lại");
            } else {
                int isCheckFind = indexDataProductIdFind(productIdFind);
                if (isCheckFind < 0) {
                    System.out.println("Không tìm thấy mã sản phẩm :" + productIdFind + " mà bạn muốn xóa thông tin");
                } else {
                    listProduct.remove(isCheckFind);
                    ProductFile.writeProductDataToFile(listProduct);
                    System.out.println("Đã xóa xong sản phẩm với mã sản phẩm là:" + productIdFind);
                }
                break;
            }
        }
    }

    //4.Hiển thị sản phẩm theo tên A-Z
    public static void displayDataProductMenuByProductNameByAZ() {
        System.out.println("Sản phẩm được sắp xếp theo tên từ A-Z là :");
        listProduct.sort(Comparator.comparing(Product::getProductName));

    }

    //5.Hiển thị sản phẩm theo lợi nhuận từ cao-thấp
    public static void displayDataProductMenuByProfitByHighToLow() {
        System.out.println("Sản phẩm được sắp xếp theo lợi nhuận từ cao-thấp là :");
        listProduct.sort(Comparator.comparing(Product::getProductProfit).reversed());
    }

    //6.Tìm kiếm các sản phẩm có tên hoặc giá nhập/xuất chứa từ khóa tìm kiếm
    public static void searchDataProductMenuByProductNamOrExportPriceOrimportPrice() {
        System.out.print("Nhập vào tên sản phẩm hoặc giá nhập/xuất của sản phẩm mà bạn muốn tìm kiếm thông tin = ");
        while (true) {
            String searchData = sc.nextLine().trim();
            if (searchData.length() == 0) {
                System.err.println("Tìm kiếm sản phẩm không được để trống,vui lòng nhập lại");
            } else {

                List<Product> listProductFilter = listProduct.stream().filter(product ->
                        product.getProductName().toLowerCase().contains(searchData.toLowerCase()) ||
                                String.valueOf(product.getImportPrice()).contains(searchData) ||
                                String.valueOf(product.getExportPrice()).contains(searchData)).collect(Collectors.toList());
                if (listProductFilter.size() == 0) {
                    System.out.println("Không tìm thấy kết quả");
                } else {
                    String repeated = new String(new char[186]).replace("\0", border);
                    System.out.println("* " + repeated + " *");
                    System.out.printf("| %-15s | %-30s | %-10s | %-10s | %-10s | %-40s | %-20s | %-30s |\n", "Mã sản phẩm", "Tên sản phẩm", "Giá nhập", "Giá bán", "Lợi nhuận", "Mô tả sản phẩm", "Trạng thái", "Tên danh mục thuộc về");
                    System.out.println("* ========================================================================================================================================================================================== *");
                    listProductFilter.forEach(Product::displayData);
                    System.out.println("* " + repeated + " *");
                }
                break;
            }

        }
    }
}
