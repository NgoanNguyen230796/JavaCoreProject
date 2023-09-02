package ra.entity;

import ra.IProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ra.entity.Category.isCheckFindIndex;
import static ra.run.Store.*;

public class Product implements IProduct, Serializable {
    private String productId;
    private String productName;
    private double importPrice;
    private double exportPrice;
    private double productProfit;
    private String productDescription;
    private Boolean productStatus;
    private int categoryId;

    public Product() {
    }

    public Product(String productId, String productName, double importPrice, double exportPrice, double productProfit,
                   String productDescription, Boolean productStatus, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.productProfit = productProfit;
        this.productDescription = productDescription;
        this.productStatus = productStatus;
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(double productProfit) {
        this.productProfit = productProfit;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void inputData() {
        System.out.print("Nhập vào mã sản phẩm (gồm 4 kí tự, bắt đầu bằng “P”)= ");
        this.productId = inputProductId();
        System.out.print("Nhập vào tên sản phẩm (gồm 6-30 kí tự)= ");
        this.productName = inputProductName();
        System.out.print("Nhập vào giá nhập của sản phẩm (số thực lơn hơn 0)= ");
        this.importPrice = inputImportPrice();
        System.out.print("Nhập vào giá bán của sản phẩm (số thực lơn hơn 0)= ");
        this.exportPrice = inputExportPrice(this.importPrice);
        System.out.print("Nhập vào mô tả của sản phẩm (không được bỏ trống)= ");
        this.productDescription = inputProductDescription();
        System.out.print("Nhập vào trạng thái của sản phẩm (true/false)= ");
        this.productStatus = inputProductStatus();
    }

    @Override
    public void displayData() {
        //String productId, String productName, double importPrice, double exportPrice, double productProfit, String productDescription, boolean productStatus
        //this.productId, this.productName, this.importPrice, this.exportPrice, this.productProfit, this.productDescription, statusValue, listCategory.get(isCheckFindIndex(this.getCategoryId())).getCategoryName()
        String statusValue = this.productStatus ? "Còn hàng" : "Ngừng kinh doanh";
        //| %-15s | %-30s | %-10s | %-10s | %-10s | %-40s | %-20s | %-30s |
        System.out.printf("| %-15s | %-30s | %-10.2f | %-10.2f | %-10.2f | %-40s | %-20s | %-30s |\n", this.productId, this.productName, this.importPrice, this.exportPrice, this.productProfit, this.productDescription, statusValue, listCategory.get(isCheckFindIndex(this.getCategoryId())).getCategoryName());
    }

    @Override
    public void calProfit() {
        this.productProfit = this.exportPrice - this.importPrice;
    }

    public static String inputProductId() {
        while (true) {
            String inputProductId = sc.nextLine().trim();
            if (inputProductId.length() == 0) {
                System.err.println("Mã sản phẩm không được để trống , vui lòng nhập lại");
            } else {
                //gồm 4 kí tự, bắt đầu bằng “P”
                boolean isCheck = isCheckTrungProductId(inputProductId);
                if (isCheck) {
                    System.err.println("Mã sản phẩm đã bị trùng, vui lòng nhập lại");
                } else {
                    if (inputProductId.length() == 4 && inputProductId.charAt(0) == 'P') {
                        return inputProductId;
                    } else {
                        System.err.println("Mã sản phẩm phải gồm 4 ký tự bắt đầu là P,vui lòng nhập lại");
                    }
                }
            }
        }
    }

    public static String inputProductName() {
        while (true) {
            //tên sản phẩm (gồm 6-30 kí tự, duy nhất)
            String inputProductName = sc.nextLine().trim();
            if (inputProductName.length() == 0) {
                System.err.println("Tên sản phẩm không được để trống , vui lòng nhập lại");
            } else {
                boolean isCheck = isCheckTrungProductName(inputProductName);
                if (isCheck) {
                    System.err.println("Tên sản phẩm đã bị trùng, vui lòng nhập lại");
                } else {
                    if (inputProductName.length() >= 6 && inputProductName.length() <= 30) {
                        return inputProductName;
                    } else {
                        System.err.println("Tên sản phẩm phải gồm 6-30 ký tự,vui lòng nhập lại");
                    }
                }
            }
        }
    }

    public static String inputUpdateProductName(int productIndex) {
        while (true) {
            //tên sản phẩm (gồm 6-30 kí tự, duy nhất)
            String inputProductName = sc.nextLine().trim();
            if (inputProductName.length() == 0) {
                System.err.println("Tên sản phẩm không được để trống , vui lòng nhập lại");
            } else {
                if (inputProductName.length() >= 6 && inputProductName.length() <= 30) {
                    boolean isCheckName = isCheckTrungUpdateProductName(productIndex, inputProductName);
                    if (isCheckName) {
                        return inputProductName;
                    } else {
                        System.err.println("Tên sản phẩm đã bị trùng, vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Tên sản phẩm phải gồm 6-30 ký tự,vui lòng nhập lại");
                }
            }
        }
    }


    public static boolean isCheckTrungProductId(String productIdNeedCheck) {
        for (Product pro : listProduct) {
            if (pro.getProductId().equalsIgnoreCase(productIdNeedCheck)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCheckTrungProductName(String productNameNeedCheck) {
        for (Product pro : listProduct) {
            if (pro.getProductName().equalsIgnoreCase(productNameNeedCheck)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCheckTrungUpdateProductName(int productIndex, String productNameNeedCheck) {
        for (Product pro : listProduct) {
            if (listProduct.get(productIndex).getCategoryId() == pro.getCategoryId()) {
                if (!listProduct.get(productIndex).getProductName().equalsIgnoreCase(productNameNeedCheck)) {
                    List<String> listCutProductName = new ArrayList<>();
                    for (Product product : listProduct) {
                        if (!product.getProductName().contains(listProduct.get(productIndex).getProductName())) {
                            listCutProductName.add(product.getProductName());
                        }
                    }
                    for (String item : listCutProductName) {
                        if (item.equalsIgnoreCase(productNameNeedCheck)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static double inputImportPrice() {
        while (true) {
            String importPriceStr = sc.nextLine().trim();
            if (importPriceStr.length() == 0) {
                System.err.println("Giá nhập sản phẩm không được để trống , vui lòng nhập lại");
            } else {
                try {
                    double importPrice = Double.parseDouble(importPriceStr);
                    if (importPrice > 0) {
                        return importPrice;
                    } else {
                        System.err.println("Giá nhập sản phẩm phải là số thực lớn hơn 0, vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Giá nhập sản phẩm phải là số thực , vui lòng nhập lại");
                }

            }
        }
    }

    public static double inputExportPrice(double importPriceNow) {
        while (true) {
            String exportPriceStr = sc.nextLine().trim();
            if (exportPriceStr.length() == 0) {
                System.err.println("Giá bán sản phẩm không được để trống , vui lòng nhập lại");
            } else {
                try {
                    double exportPrice = Double.parseDouble(exportPriceStr);
                    if (exportPrice > 0) {
                        double exportPriceRank = (MIN_INTEREST_RATE + 1) * importPriceNow;
                        if (exportPrice > exportPriceRank) {
                            return exportPrice;
                        } else {
                            System.err.printf("%s%s%.1f%s\n", "Giá bán sản phẩm phải lớn hơn giá nhập ít nhất 0.2 lần", " tức là ( > ", exportPriceRank, "), vui lòng nhập lại");
                        }
                    } else {
                        System.err.println("Giá bán sản phẩm phải là số thực lớn hơn 0, vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Giá bán sản phẩm phải là số thực , vui lòng nhập lại");
                }

            }
        }
    }

    public static String inputProductDescription() {
        while (true) {
            String productDescription = sc.nextLine().trim();
            if (productDescription.length() == 0) {
                System.err.println("Mô tả sản phẩm không được để trống, vui lòng nhập lại");
            } else {
                return productDescription;
            }
        }
    }

    public static boolean inputProductStatus() {
        while (true) {
            String productStatus = sc.nextLine().trim();
            if (productStatus.length() == 0) {
                System.err.println("Trạng thái sản phẩm không được để trống, vui lòng nhập lại");
            } else if (productStatus.equalsIgnoreCase("true") || productStatus.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(productStatus);
            } else {
                System.err.println("Trạng thái sản phẩm phải là true/false, vui lòng nhập lại");
            }
        }
    }

    public static boolean isCheckCatalogIdInListProduct(int inputCatalogId) {
        for (Product pr : listProduct) {
            if (pr.getCategoryId() == inputCatalogId) {
                return true;
            }
        }
        return false;
    }

    public static int indexDataProductIdFind(String productIdFind) {
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).productId.equals(productIdFind)) {
                return i;
            }
        }
        return -1;
    }


}
