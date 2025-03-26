package elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.Model;

public class NoticeHistoryModel {
    private String totalNotice;
    private String printingPendingNotice;
    private String totalNoticeCount;
    private String totalNoticeAmount;
    private String noticeDate;

    public NoticeHistoryModel(){}

    public NoticeHistoryModel(String totalNotice, String printingPendingNotice, String totalNoticeCount, String totalNoticeAmount, String noticeDate) {
        this.totalNotice = totalNotice;
        this.printingPendingNotice = printingPendingNotice;
        this.totalNoticeCount = totalNoticeCount;
        this.totalNoticeAmount = totalNoticeAmount;
        this.noticeDate = noticeDate;
    }

    public String getTotalNotice() {
        return totalNotice;
    }

    public void setTotalNotice(String totalNotice) {
        this.totalNotice = totalNotice;
    }

    public String getPrintingPendingNotice() {
        return printingPendingNotice;
    }

    public void setPrintingPendingNotice(String printingPendingNotice) {
        this.printingPendingNotice = printingPendingNotice;
    }

    public String getTotalNoticeCount() {
        return totalNoticeCount;
    }

    public void setTotalNoticeCount(String totalNoticeCount) {
        this.totalNoticeCount = totalNoticeCount;
    }

    public String getTotalNoticeAmount() {
        return totalNoticeAmount;
    }

    public void setTotalNoticeAmount(String totalNoticeAmount) {
        this.totalNoticeAmount = totalNoticeAmount;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    @Override
    public String toString() {
        return "NoticeHistoryModel{" +
                "totalNotice='" + totalNotice + '\'' +
                ", printingPendingNotice='" + printingPendingNotice + '\'' +
                ", totalNoticeCount='" + totalNoticeCount + '\'' +
                ", totalNoticeAmount='" + totalNoticeAmount + '\'' +
                ", noticeDate='" + noticeDate + '\'' +
                '}';
    }
}
