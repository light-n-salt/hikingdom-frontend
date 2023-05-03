import apiRequest from 'apis/axios'

// GET Request
export function checkNickname(nickname: string) {
    return apiRequest.get(`/members/auth/nickname-check/${nickname}`)
}

// POST Request
export function validEmail(email: string) {
    return apiRequest.post(`/members/auth/email-valid`, {
        email,
    })
}

export function signup(
    email: string,
    nickname: string,
    password: string,
    checkPassword: string
) {
    return apiRequest.post(`/members/auth/signup`, {
        email,
        nickname,
        password,
        checkPassword,
    })
}

export function login(email: string, password: string) {
    return apiRequest.post(`/members/auth/login`, {
        email,
        password,
    })
}

export function updateNickname(nickname: string) {
    return apiRequest.post(`/members/nicname-change`, {
        nickname,
    })
}

// DELETE Request
export function confirmEmail(email: string, authCode: string) {
    return apiRequest.delete(`/members/auth/email-valid`, {
        data: {
            email,
            authCode,
        },
    })
}
