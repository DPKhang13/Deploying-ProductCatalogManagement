# Script kiểm tra trạng thái Docker
Write-Host "🔍 Kiểm tra trạng thái Docker containers..." -ForegroundColor Cyan
Write-Host ""

# Kiểm tra Docker có đang chạy không
try {
    docker ps | Out-Null
    Write-Host "✅ Docker Desktop đang chạy" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker Desktop chưa chạy. Vui lòng mở Docker Desktop!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "📦 Containers:" -ForegroundColor Yellow
docker ps -a --filter "name=fpt-ecommerce" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

Write-Host ""
Write-Host "🌐 Networks:" -ForegroundColor Yellow
docker network ls --filter "name=ecommerce"

Write-Host ""
Write-Host "💾 Volumes:" -ForegroundColor Yellow
docker volume ls --filter "name=sqlserver"

Write-Host ""
Write-Host "📊 Resource Usage:" -ForegroundColor Yellow
docker stats --no-stream --filter "name=fpt-ecommerce"

Write-Host ""
