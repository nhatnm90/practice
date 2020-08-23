package bank;
import utils.StringFormat;

import java.text.SimpleDateFormat;
import java.util.*;

public class Account {

    //<editor-fold desc="Constant">
    private static final long INITIALIZED_BALANCE = 100;

    public enum Period {
        Week,
        Month,
        Quarter,
        Year
    }
    //</editor-fold>

    //<editor-fold desc="Propeties">
    private long accountId;
    private String firstName;
    private String lastName;
    private Calendar createdDate;
    private float rate;
    private long currentBalance;
    private Calendar endDate;
    private List<AccountHistory> histories;
    private Period period;
    //</editor-fold>

    //<editor-fold desc="Getter Setter">
    public long getAccountId() {
        return accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setHistories(List<AccountHistory> histories) {
        this.histories = histories;
    }

    public List<AccountHistory> getHistories() {
        return histories;
    }

    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Account(long accountId, String firstName, String lastName) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = Calendar.getInstance();
        this.currentBalance = INITIALIZED_BALANCE;
    }

    public Account(long accountId, String firstName, String lastName, Period period) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = Calendar.getInstance();
        this.currentBalance = INITIALIZED_BALANCE;
        this.period = period;
        this.histories = new ArrayList<>();
    }

    public Account(long accountId, String firstName, String lastName, Calendar createdDate, Calendar endDate,
                   long currentBalance, Period period) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.currentBalance = currentBalance;
        this.period = period;
        this.rate = generateRate();
        this.histories = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold desc="Private functions">
    private float generateRate(){
        switch (this.period) {
            case Week:
                return 3;
            case Month:
                return 5;
            case Quarter:
                return 10;
            case Year:
                return 16;
        }
        return 0;
    }

    /*
     * Tính ra số ngày cần cộng thêm theo chu kì, tự quy định chu kì Week +7, Month +30, Quarter +120, Year +365
     * */
    private int generateNumberOfAddedDays() {
        switch (this.period) {
            case Week:
                return 7;
            case Month:
                return 30;
            case Quarter:
                return 120;
            case Year:
                return 365;
        }
        return 0;
    }

    /*
     * Tính ra số lượng chu kì đã trả qua cho khách hàng đó tính từ ngày start -> end
     * Vd: khách hàng chọn
     * Khách hàng 1
     * - period: week
     * - startDate:  01-01-2020
     * - endDate:    06-01-2020
     * -> chưa qua đc chu kì nào -> result = 0
     *
     * Khách hàng 2
     * - period: week
     * - startDate:  01-01-2020
     * - endDate:    15-01-2020
     * -> Đã qua đc 2 chu kì (2 tuần) -> result = 2
     * */
    private int generateNumberOfExpiredDate() {
        // Lấy ngày hiện tại cộng với số ngày cần thêm vào theo Period
        // Nếu đã sau ngày kết thúc -> return 0 do chưa thỏa đc bất kì kì hạn nào
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, generateNumberOfAddedDays());
        if (startDate.after(this.endDate)) {
            return 0;
        }
        int result = 0;
        do {
            startDate.add(Calendar.DATE, generateNumberOfAddedDays());
            result += 1;
        }
        while (startDate.before(this.endDate));
        return result;
    }

    private String formatDate(Calendar date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date currentDate = date.getTime();
        return dateFormat.format(currentDate);
    }
    //</editor-fold>

    //<editor-fold desc="Public functions">
    public static void dash(){
        System.out.println("----------------------------------------");
    }

    /*
     * Show Info
     * */
    public void showInfo() {
        System.out.println("- Số tài khoản: " + accountId);
        System.out.println("- Tên + Họ: " + firstName + " " + lastName);
        System.out.println("- Số tiền trong tài khoản: " + currentBalance);
        System.out.println("- Ngày tạo tài khoản: " + formatDate(createdDate, "dd-MM-YYYY"));
        System.out.println("- Lịch sử giao dịch: ");
        dash();
        if (histories.isEmpty()){
            System.out.println("- Khách hàng chưa có giao dịch");
            dash();
        } else {
            Collections.sort(histories, Comparator.comparing(AccountHistory::getCreatedDate).reversed());
            for (AccountHistory accountHistory : histories) {
                System.out.println("- Ngày giao dịch: " + StringFormat.formatDate(accountHistory.getCreatedDate(), "dd-MM-yyyy hh:mm:ss"));
                System.out.println("- Số tiền: " + accountHistory.getBalance());
                System.out.println("- Loại giao dịch: " + accountHistory.translatedType());
                dash();
            }
        }

    }

    /*
     * Recalculate current balance when adding more money
     * Add histories to Account History list
     *  @param value of money
     * */
    public void addMoney(long value, AccountHistory.Type type) {
        currentBalance = currentBalance + value;
        if(type == AccountHistory.Type.in){
            AccountHistory history = new AccountHistory(value, AccountHistory.Type.in);
            this.histories.add(history);
        } else if (type == AccountHistory.Type.transferIn){
            AccountHistory history = new AccountHistory(value, AccountHistory.Type.transferIn, accountId);
            this.histories.add(history);
        }




//            AccountHistory history = new AccountHistory(value, AccountHistory.Type.transferOut);
//            currentHistories.add(history);
//            sourceAccount.setHistories(currentHistories);
    }
    /*
     * Recalculate current balance when subtracting money
     * Add histories to Account History list
     * @param value of money
     * */
    public void subMoney(long value, AccountHistory.Type type) {
        if ((currentBalance - 50000) >= value) {
            currentBalance = currentBalance - value;
        } else {
            System.out.println("Số tiền trong tài khoản không đủ");
        }
        if(type == AccountHistory.Type.out){
            AccountHistory history = new AccountHistory(value, AccountHistory.Type.out);
            this.histories.add(history);
        } else if (type == AccountHistory.Type.transferOut){
            AccountHistory history = new AccountHistory(value, AccountHistory.Type.transferOut, accountId);
            this.histories.add(history);
        }
    }

    /*
     * Recalculate current balance with rate
     * */
    public void recalculatedBalance() {
        currentBalance = currentBalance + (currentBalance * (long) rate) / 100;
    }

    /*
     * Hàm tính tổng tiền lãnh
     * Dựa vào số chu kì đã đc tính bên trên, loop qua danh sách chu kì và cộng đồn tiền theo công thức
     * */
    public void calculateCurrentBalance() {
        int noOfRate = this.generateNumberOfExpiredDate();

        for (int i = 1; i <= noOfRate; i++) {
            this.recalculatedBalance();
        }
    }
    //</editor-fold>
}
    /*
     * 1. Tạo constructor để tạo đối tượng
     * - số tài khoản
     * - tên + họ
     * - số tiền mặc định ban đầu là 50k
     * - ngày tạo là ngày hiện tại
     *
     * 2. Viết hàm in ra thông tin đối tượng vừa tạo
     *
     * 3. Viết hàm thực hiện thao tác nạp tiền, rút tiền 1 cách hoàn chỉnh (kiểm tra đầu vào đầu ra)
     *
     * 4. Tạo constructor để tạo đối tượng có tham số truyền vào là kỳ hạn (Period) và tính ra lãi suất theo công thức sau
     *  - Week: 3%
     *  - Month: 5%
     *  - Quarter: 10%
     *  - Year: 16%
     *
     * 5. Viết hàm thực hiện phương thức đáo hạn
     *  - Mỗi lần gọi phương thức này sẽ tính lại currentBalance
     *      currentBalance = currentBalance + currentBalance*rate
     *
     * 6. * Thêm vào 1 thuộc tính là ngày quyết toán (ngày kết thúc kì hạn gửi tiền)
     *  - Tính toán số tiền cuối cùng nhận được theo công thức sau: TBD
     * */



