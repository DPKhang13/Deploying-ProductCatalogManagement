# Hướng dẫn Deploy lên Docker

## Yêu cầu

- Docker Desktop đã được cài đặt
- Docker Compose (đi kèm với Docker Desktop)

## Cách 1: Sử dụng Docker Compose (Khuyến nghị)

### Build và chạy ứng dụng:

```bash
docker-compose up -d --build
```

### Xem logs:

```bash
docker-compose logs -f
```

### Dừng ứng dụng:

```bash
docker-compose down
```

### Truy cập ứng dụng:

Mở trình duyệt và truy cập: http://localhost:3000

## Cách 2: Sử dụng Docker trực tiếp

### Build Docker image:

```bash
docker build -t ecommerce-frontend:latest .
```

### Chạy container:

```bash
docker run -d -p 3000:80 --name ecommerce-frontend ecommerce-frontend:latest
```

### Xem logs:

```bash
docker logs -f ecommerce-frontend
```

### Dừng container:

```bash
docker stop ecommerce-frontend
docker rm ecommerce-frontend
```

## Quản lý Docker Image

### Xem danh sách images:

```bash
docker images
```

### Xóa image:

```bash
docker rmi ecommerce-frontend:latest
```

### Xem danh sách containers:

```bash
docker ps -a
```

## Deploy lên Docker Hub (Optional)

### 1. Đăng nhập Docker Hub:

```bash
docker login
```

### 2. Tag image:

```bash
docker tag ecommerce-frontend:latest your-dockerhub-username/ecommerce-frontend:latest
```

### 3. Push lên Docker Hub:

```bash
docker push your-dockerhub-username/ecommerce-frontend:latest
```

### 4. Pull và chạy từ Docker Hub (trên server khác):

```bash
docker pull your-dockerhub-username/ecommerce-frontend:latest
docker run -d -p 3000:80 your-dockerhub-username/ecommerce-frontend:latest
```

## Cấu hình API Endpoint

Nếu cần thay đổi API endpoint, hãy cập nhật file `src/constants/apiEndPoints.ts` trước khi build Docker image.

## Troubleshooting

### Container không start:

```bash
# Xem logs chi tiết
docker logs ecommerce-frontend

# Kiểm tra port đã được sử dụng chưa
netstat -ano | findstr :3000
```

### Build lỗi:

```bash
# Xóa cache và build lại
docker-compose build --no-cache
```

### Port bị conflict:

Thay đổi port trong `docker-compose.yml`:

```yaml
ports:
  - "8080:80" # Thay 3000 thành port khác
```

## Production Tips

1. **Environment Variables**: Tạo file `.env.production` cho các biến môi trường
2. **SSL/HTTPS**: Sử dụng reverse proxy như Nginx hoặc Traefik
3. **Monitoring**: Cân nhắc sử dụng Portainer để quản lý Docker UI
4. **Auto-restart**: Container được cấu hình `restart: unless-stopped`

## Deploy lên Railway

### Yêu cầu:

- Tài khoản Railway (https://railway.app)
- Repository GitHub đã push code

### Các bước deploy:

1. **Đăng nhập Railway và tạo project mới**
   - Truy cập: https://railway.app
   - Click "New Project"
   - Chọn "Deploy from GitHub repo"

2. **Kết nối GitHub repository**
   - Chọn repository chứa code
   - Railway sẽ tự động phát hiện Dockerfile

3. **Cấu hình Environment Variables** ⚠️ **QUAN TRỌNG**
   - Trong Railway Dashboard, vào tab "Variables"
   - Thêm biến môi trường sau:
     ```
     VITE_API_URL=https://your-backend-api.railway.app
     ```
   - Thay `your-backend-api.railway.app` bằng URL backend API của bạn
   - Nếu chưa có backend, có thể bỏ qua bước này và thêm sau

4. **Cấu hình (tự động)**
   - Railway sẽ sử dụng file `railway.json` để cấu hình
   - PORT environment variable sẽ được tự động inject

5. **Deploy**
   - Railway sẽ tự động build và deploy
   - Sau khi deploy xong, bạn sẽ nhận được URL public

### Lưu ý về Railway:

- Railway tự động cung cấp PORT environment variable (thường là 80, 443, hoặc số ngẫu nhiên)
- Nginx config đã được cấu hình để sử dụng `${PORT}` variable
- Railway tự động cung cấp HTTPS certificate
- Mỗi lần push code lên GitHub, Railway sẽ tự động re-deploy

### Troubleshooting Railway:

**Lỗi "Application failed to respond":**

- Kiểm tra logs: Railway Dashboard → Deployments → View Logs
- Đảm bảo app đang listen trên PORT environment variable

**Build failed:**

```bash
# Kiểm tra build locally trước:
docker build -t test-build .
docker run -p 8080:80 -e PORT=80 test-build
```

**API endpoint issues:**

- Cập nhật file `src/constants/apiEndPoints.ts` với URL Railway backend của bạn
- Rebuild và redeploy
