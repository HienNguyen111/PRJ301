<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    /* Footer Styles */
    .footer {
        background: linear-gradient(135deg, #FAD105, #F8B400);
        color: white;
        padding: 3rem 1rem;
        margin-top: 2rem;
        text-align: center;
        box-shadow: 0 -5px 15px rgba(0, 0, 0, 0.1);
        border-top-left-radius: 20px;
        border-top-right-radius: 20px;
    }

    /* Footer Layout */
    .footer-container {
        max-width: 1200px;
        margin: 0 auto;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        gap: 2rem;
        padding-bottom: 2rem;
    }

    /* Footer Section */
    .footer-section {
        flex: 1;
        min-width: 250px;
        text-align: left;
    }

    .footer-section h3 {
        font-size: 1.3rem;
        margin-bottom: 1rem;
        color: #0590C9;
        text-transform: uppercase;
    }

    /* Footer Text */
    .footer-section p {
        font-size: 1rem;
        line-height: 1.6;
        opacity: 0.9;
    }

    /* Footer Links */
    .footer-links {
        list-style: none;
        padding: 0;
    }

    .footer-links li {
        margin-bottom: 0.6rem;
    }

    .footer-links a {
        color: white;
        text-decoration: none;
        font-size: 1rem;
        transition: all 0.3s ease-in-out;
    }

    .footer-links a:hover {
        color: #3498db;
        transform: translateX(5px);
    }

    /* Social Links */
    .social-links {
        display: flex;
        gap: 1rem;
        justify-content: center;
        margin-top: 1rem;
    }

    .social-links a {
        color: white;
        text-decoration: none;
        font-size: 1.8rem;
        transition: transform 0.3s ease;
    }

    .social-links a:hover {
        transform: scale(1.2);
    }

    /* Copyright */
    .copyright {
        text-align: center;
        padding-top: 2rem;
        border-top: 2px solid rgba(255, 255, 255, 0.2);
        font-size: 0.95rem;
        opacity: 0.8;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .footer-container {
            flex-direction: column;
            text-align: center;
        }

        .footer-section {
            text-align: center;
        }

        .social-links {
            justify-content: center;
        }
    }

</style>

<footer class="footer">
    <div class="footer-container">
        <div class="footer-section">
            <h3>Về chúng tôi</h3>
            <p>Online Examination cho phép giảng viên tạo, phân loại kỳ thi, thêm câu hỏi và chấm điểm tự động. Sinh viên có thể làm bài và xem kết quả nhanh chóng, giúp tối ưu hóa quy trình thi cử.</p>
        </div>
        
        <div class="footer-section">
            <h3>Liên kết nhanh</h3>
            <ul class="footer-links">
                <li><a href="#">Trang chủ</a></li>
                <li><a href="#">Thông tin</a></li>
                <li><a href="#">Chính sách</a></li>
            </ul>
        </div>
        
        <div class="footer-section">
            <h3>Liên hệ</h3>
            <p>Địa chỉ: 123 Đường ABC, Quận XYZ</p>
            <p>Email: contact@onlineExamination.com</p> 
            <p>Điện thoại: (84) 123-456-789</p>
        </div>
        
        <div class="footer-section">
            <h3>Theo dõi chúng tôi</h3>
            <p>Cập nhật tin tức mới nhất từ chúng tôi</p>
            <div class="social-links">
                <a href="#">📱</a>
                <a href="#">💬</a>
                <a href="#">📷</a>
            </div>
        </div>
    </div>
    
    <div class="copyright">
        <p>&copy; 2025 Online Examination. Tất cả quyền được bảo lưu.</p>
    </div>
</footer>
