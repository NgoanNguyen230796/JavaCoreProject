package ra.entity;

import ra.ICategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ra.run.Store.listCategory;
import static ra.run.Store.sc;

public class Category implements ICategory, Serializable {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private Boolean categoryStatus;


    public Category() {
    }

    public Category(int categoryId, String categoryName, String categoryDescription, Boolean categoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryStatus = categoryStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Boolean getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(Boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    @Override
    public void inputData() {
        System.out.print("Nhập vào mã danh mục (phải là số)= ");
        this.categoryId = inputCategoryId();
        System.out.print("Nhập vào tên danh mục = ");
        this.categoryName = inputCategoryName();
        System.out.print("Nhập vào mô tả danh mục = ");
        this.categoryDescription = inputCategoryDescription();
        System.out.print("Nhập vào trạng thái danh mục (true/false) = ");
        this.categoryStatus = inputCategoryStatus();
    }

    @Override
    public void displayData() {
        //int categoryId, String categoryName, String categoryDescription, Boolean categoryStatus
        String active = this.categoryStatus ? "Hoạt động" : "Không hoạt động";
        System.out.printf("| %-15s | %-40s | %-40s | %-20s|\n", this.categoryId, this.categoryName, this.categoryDescription, active);
    }

    public static int inputCategoryId() {
        while (true) {
            String inputIdStr = sc.nextLine().trim();
            if (inputIdStr.length() == 0) {
                System.err.println("Mã danh mục phải không được để trống, vui lòng nhập lại");
            } else {
                try {
                    int id = Integer.parseInt(inputIdStr);
                    if (id > 0) {
                        boolean isCheck = isCheckTrungCategoryId(id);
                        if (isCheck) {
                            System.err.println("Mã danh mục đã bị trùng, vui lòng nhập lại");
                        } else {
                            return id;
                        }
                    } else {
                        System.err.println("Mã danh mục phải là số nguyên lớn hơn 0, vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Mã danh mục phải là số, vui lòng nhập lại");
                }
            }
        }
    }

    public static boolean isCheckTrungCategoryId(int categoryIdNeedCheck) {
        for (Category ca : listCategory) {
            if (ca.getCategoryId() == categoryIdNeedCheck) {
                return true;
            }
        }
        return false;
    }

    public static String inputCategoryName() {
        //Tên danh mục (không trùng nhau, từ 6-30 kí tự)
        while (true) {
            String name = sc.nextLine().trim();
            if (name.length() == 0) {
                System.err.println("Tên danh mục không được để trống, vui lòng nhập lại");
            } else {
                if (name.length() >= 6 && name.length() <= 30) {
                    boolean isCheckName = isCheckTrungCategoryName(name);
                    if (isCheckName) {
                        System.err.println("Tên danh mục đã bị trùng, vui lòng nhập lại");
                    } else {
                        return name;
                    }
                } else {
                    System.err.println("Tên danh mục phải có độ dài từ 6-30 ký tự, vui lòng nhập lại");
                }
            }
        }
    }

    public static String inputUpdateCategoryName(int categoryIndex) {
        //Tên danh mục (không trùng nhau, từ 6-30 kí tự)
        while (true) {
            String name = sc.nextLine().trim();
            if (name.length() == 0) {
                System.err.println("Tên danh mục không được để trống, vui lòng nhập lại");
            } else {
                if (name.length() >= 6 && name.length() <= 30) {
                    boolean isCheckName = isCheckTrungUpdateCategoryName(categoryIndex, name);
                    if (isCheckName) {
                        return name;
                    } else {
                        System.err.println("Tên danh mục đã bị trùng, vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Tên danh mục phải có độ dài từ 6-30 ký tự, vui lòng nhập lại");
                }
            }
        }
    }

    public static boolean isCheckTrungCategoryName(String categoryNameNeedCheck) {
        for (Category ca : listCategory) {
            if (ca.getCategoryName().equalsIgnoreCase(categoryNameNeedCheck)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCheckTrungUpdateCategoryName(int categoryIndex, String categoryNameNeedCheck) {
        for (Category ca : listCategory) {
            if (listCategory.get(categoryIndex).getCategoryId() == ca.getCategoryId()) {
                if (!listCategory.get(categoryIndex).getCategoryName().equalsIgnoreCase(categoryNameNeedCheck)) {
                    List<String> listCut = new ArrayList<>();
                    for (Category category : listCategory) {
                        if (!category.getCategoryName().contains(listCategory.get(categoryIndex).getCategoryName())) {
                            listCut.add(category.getCategoryName());
                        }
                    }
                    for (String item : listCut) {
                        if (item.equalsIgnoreCase(categoryNameNeedCheck)) {
                            return false;
                        }
                    }

                }
                return true;
            }
        }
        return false;

    }


    public static String inputCategoryDescription() {
        while (true) {
            String description = sc.nextLine().trim();
            if (description.length() == 0) {
                System.err.println("Mô tả danh mục không được để trống, vui lòng nhập lại");
            } else {
                return description;
            }
        }
    }

    public static boolean inputCategoryStatus() {
        while (true) {
            String status = sc.nextLine().trim();
            if (status.length() == 0) {
                System.err.println("Trạng thái danh mục không được để trống, vui lòng nhập lại");
            } else if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(status);
            } else {
                System.err.println("Trạng thái danh mục phải là true/false, vui lòng nhập lại");
            }
        }
    }

    public static int isCheckFindCategoryId() {
        while (true) {
            String inputIdStr = sc.nextLine().trim();
            if (inputIdStr.length() == 0) {
                System.err.println("Mã danh mục không được để trống,vui lòng nhập lại");
            } else {
                try {
                    int id = Integer.parseInt(inputIdStr);
                    if (id > 0) {
                        for (int i = 0; i < listCategory.size(); i++) {
                            if (listCategory.get(i).getCategoryId() == id) {
                                return i;
                            }
                        }
                        return -1;
                    } else {
                        System.err.println("Mã danh mục phải là số nguyên lớn hơn 0,vui lòng nhập lại");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Mã danh mục phải là số,vui lòng nhập lại");
                }
            }
        }
    }

    @Override
    public String toString() {
        return categoryName;
    }

    public static int isCheckFindIndex(int id) {
        for (int i = 0; i < listCategory.size(); i++) {
            if (listCategory.get(i).getCategoryId() == id) {
                return i;
            }
        }
        return -1;
    }
}
