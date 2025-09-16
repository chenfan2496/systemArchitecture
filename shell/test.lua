wrk.method = "POST"
wrk.headers["Content-Type"] = "application/json"

function request()
    local randomId = math.random(1000, 9999) -- 生成随机数
    local body = string.format('{"userId": %d, "amount": 100}', randomId)
    return wrk.format("POST", nil, wrk.headers, body) -- 使用 wrk.format 生成请求
end