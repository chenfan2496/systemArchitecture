wrk.method = "POST"
wrk.headers["Content-Type"] = "application/json"

function request()
    local itemId = math.random(1, 100000) -- 生成随机数
    local userId = math.random(1, 100000)
    local userId = math.random(1, 100000)
    local body = string.format('{"userId": %d, "amount": 100}', userId,itemId,)
    return wrk.format("POST", nil, wrk.headers, body) -- 使用 wrk.format 生成请求
end