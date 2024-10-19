import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Tính trừu tượng 
abstract class SanPham {
    private String ten;
    private double gia;
    private int soLuong;

    // Tính đóng gói 
    public SanPham(String ten, double gia, int soLuong) {
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public abstract double tinhGia();
    public String getTen() { return ten; }
    public double getGia() { return gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    // Tính đa hình
    @Override
    public abstract String toString();
}

// Tính kế thừa
class SanPhamCoSan extends SanPham {
    public SanPhamCoSan(String ten, double gia, int soLuong) {
        super(ten, gia, soLuong);
    }
    
    @Override
    public double tinhGia() {
        return getGia() * getSoLuong();
    }
    
    @Override
    public String toString() {
        return String.format("%s - Gia: %.2f VND, So luong: %d, Tong: %.2f VND",
                getTen(), getGia(), getSoLuong(), tinhGia());
    }
}

class GioHang {
    private List<SanPham> danhSachSanPham = new ArrayList<>();

    // Các phương thức công khai để tương tác với giỏ hàng
    public void themSanPham(SanPham sanPham) {
        danhSachSanPham.add(sanPham);
        System.out.println("Da them: " + sanPham);
        hienThiGioHang();
    }

    public void xoaSanPham(int index) {
        if (index >= 0 && index < danhSachSanPham.size()) {
            System.out.println("Da xoa: " + danhSachSanPham.remove(index));
            hienThiGioHang();
        } else {
            System.out.println("Khong tim thay san pham de xoa.");
        }
    }

    public void capNhatSoLuong(int index, int soLuongMoi) {
        if (index >= 0 && index < danhSachSanPham.size()) {
            danhSachSanPham.get(index).setSoLuong(soLuongMoi);
            System.out.println("Da cap nhat: " + danhSachSanPham.get(index));
            hienThiGioHang();
        } else {
            System.out.println("Khong tim thay san pham de cap nhat so luong.");
        }
    }

    public void hienThiGioHang() {
        System.out.println("\n===== GIO HANG =====");
        if (danhSachSanPham.isEmpty()) {
            System.out.println("Gio hang trong.");
        } else {
            for (int i = 0; i < danhSachSanPham.size(); i++) {
                System.out.println((i + 1) + ". " + danhSachSanPham.get(i));
            }
            System.out.println("Tong gia: " + tinhTongGia() + " VND");
        }
    }

    public double tinhTongGia() {
        return danhSachSanPham.stream().mapToDouble(SanPham::tinhGia).sum();
    }

    public boolean isEmpty() {
        return danhSachSanPham.isEmpty();
    }

    public void xoaTatCaSanPham() {
        danhSachSanPham.clear();
    }

    public List<SanPham> getDanhSachSanPham() {
        return danhSachSanPham;
    }
}

// Tính đóng gói 
class ThanhToan {
    public static boolean thucHienThanhToan(double soTien, boolean laNganHang, double giamGia) {
        double soTienSauGiamGia = soTien * (1 - giamGia / 100);
        if (laNganHang) {
            System.out.println("Dang xu ly thanh toan qua ngan hang...");
            System.out.println("Nhap ma OTP: ");
            Scanner scanner = new Scanner(System.in);
            String otp = scanner.nextLine();
            System.out.println("Dang xac thuc OTP...");
            System.out.printf("Thanh toan thanh cong so tien: %.2f VND (Da giam gia %.0f%%)\n",
                    soTienSauGiamGia, giamGia);
            return true;
        } else {
            System.out.printf("Vui long thanh toan %.2f VND tai quay. (Da giam gia %.0f%%)\n",
                    soTienSauGiamGia, giamGia);
            return true;
        }
    }
}

// Lớp chính điều khiển luồng chương trình
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GioHang gioHang = new GioHang();
    private static final List<SanPhamCoSan> danhSachSanPhamCoSan = new ArrayList<>();

    // Khởi tạo danh sách sản phẩm có sẵn
    static {
        danhSachSanPhamCoSan.add(new SanPhamCoSan("May quay video", 849.90, 1));
        danhSachSanPhamCoSan.add(new SanPhamCoSan("May nghe nhac", 249.90, 1));
        danhSachSanPhamCoSan.add(new SanPhamCoSan("May anh DSLR", 1299.90, 1));
        danhSachSanPhamCoSan.add(new SanPhamCoSan("May nghe nhac Hi-Fi", 399.90, 1));
    }

    public static void main(String[] args) {
        while (true) {
            hienThiDanhSachSanPham();
            gioHang.hienThiGioHang();
            hienThiMenu();
            int luaChon = scanner.nextInt();
            if (luaChon == 0) break;
            xuLyLuaChon(luaChon);
        }
        System.out.println("Cam on ban da su dung dich vu!");
    }

    private static void hienThiDanhSachSanPham() {
        System.out.println("\n===== DANH SACH SAN PHAM =====");
        for (int i = 0; i < danhSachSanPhamCoSan.size(); i++) {
            SanPhamCoSan sp = danhSachSanPhamCoSan.get(i);
            System.out.printf("%d. %s\n", i + 1, sp);
        }
    }

    private static void hienThiMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Them san pham vao gio hang");
        System.out.println("2. Sua so luong san pham");
        System.out.println("3. Xoa san pham khoi gio hang");
        System.out.println("4. Thanh toan");
        System.out.println("0. Thoat");
        System.out.print("Lua chon cua ban: ");
    }

    private static void xuLyLuaChon(int luaChon) {
        switch (luaChon) {
            case 1: themSanPham(); break;
            case 2: suaSoLuong(); break;
            case 3: xoaSanPham(); break;
            case 4: thanhToan(); break;
            default: System.out.println("Lua chon khong hop le.");
        }
    }

    private static void themSanPham() {
        System.out.print("Chon san pham (1-" + danhSachSanPhamCoSan.size() + "): ");
        int chon = scanner.nextInt() - 1;
        System.out.print("Nhap so luong: ");
        int soLuong = scanner.nextInt();

        if (chon >= 0 && chon < danhSachSanPhamCoSan.size()) {
            SanPhamCoSan sp = danhSachSanPhamCoSan.get(chon);
            gioHang.themSanPham(new SanPhamCoSan(sp.getTen(), sp.getGia(), soLuong));
        } else {
            System.out.println("San pham khong ton tai.");
        }
    }

    private static void suaSoLuong() {
        if (gioHang.isEmpty()) {
            System.out.println("Gio hang trong.");
            return;
        }
        System.out.print("Chon san pham can sua: ");
        int chon = scanner.nextInt() - 1;
        System.out.print("Nhap so luong moi: ");
        int soLuongMoi = scanner.nextInt();
        gioHang.capNhatSoLuong(chon, soLuongMoi);
    }

    private static void xoaSanPham() {
        if (gioHang.isEmpty()) {
            System.out.println("Gio hang trong.");
            return;
        }
        System.out.print("Chon san pham can xoa: ");
        int chon = scanner.nextInt() - 1;
        gioHang.xoaSanPham(chon);
    }

    private static void thanhToan() {
        if (gioHang.isEmpty()) {
            System.out.println("Gio hang trong.");
            return;
        }
        System.out.print("Nhap ma giam gia (neu co, nhap 0 neu khong co): ");
        double phanTramGiamGia = scanner.nextDouble();
        System.out.print("Chon phuong thuc thanh toan (1: Ngan hang, 2: Tien mat): ");
        int phuongThuc = scanner.nextInt();
        boolean thanhToanThanhCong = ThanhToan.thucHienThanhToan(gioHang.tinhTongGia(), phuongThuc == 1, phanTramGiamGia);
        if (thanhToanThanhCong) {
            gioHang.xoaTatCaSanPham();
            System.out.println("Da thanh toan thanh cong.");
        }
    }
}
