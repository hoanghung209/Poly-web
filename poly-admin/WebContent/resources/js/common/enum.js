var ENUM = new Object();
ENUM.ObjectStatus = {         
    IsActived :1, // Có hiệu lực
    IsLocked :2,  // Bị khóa
    IsEditing :3,  //Đang tiến hành dở
    IsWaiting :4, //chờ duyệt, mới gửi lên
    IsApproved :5,//Bài đã duyệt, chờ xuất bản
    IsPublished :6,//Bài đã xuất bản
    IsDenied :7, //Đã gửi trả lại
    IsStopPublished :8,//Bài đã xuất bản xong, và đã hạ xuống
    IsSuccess :9,  //Đã hoàn thành 
    IsApproving :10,//Đang biên tập dở
    IsNoSend :11,
    IsSended :12,
    IsPreviewSended :13,
};