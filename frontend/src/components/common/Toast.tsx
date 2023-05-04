import React from 'react'
import styles from './Toast.module.scss'
import { createRoot } from 'react-dom/client'
import {
  FaCheckCircle,
  FaTimesCircle,
  FaInfoCircle,
  FaExclamationCircle,
} from 'react-icons/fa'

type Message = {
  id: number
  type: 'success' | 'error' | 'info' | 'warning'
  message: string
}

class ToastManager {
  root
  messages: Message[]

  // 생성자 함수. index.html의 'toast-root' 아이디에 해당하는 요소를 선택한후
  // createRoot로 해당 컴포넌트 안을 기준으로 잡는 root 생성
  constructor() {
    const rootElem = document.getElementById('toast-root') as HTMLElement
    this.root = createRoot(rootElem)
    this.messages = []
  }

  // 토스트를 닫는 함수
  deleteMessage(id: number) {
    const newMessages = this.messages.filter((message) => message.id !== id)
    this.messages = newMessages
    this.root.render(
      <ToastMessages
        messages={this.messages}
        deleteMessage={this.deleteMessage.bind(this)} // 함수 안에 선언된 this.(변수)를 prop받은 컴포넌트에서도 사용할 수 있도록 bind
      />
    )
  }

  // 3초뒤 자동으로 토스트를 닫는 함수
  autoDeleteMessage(id: number) {
    //clearTimeout은 반드시 필요하지 않음. https://stackoverflow.com/questions/7391567/when-using-settimeout-do-you-have-to-cleartimeout
    setTimeout(
      () => {
        const toastElem = document.getElementById(
          `toast-message-${id}`
        ) as HTMLElement
        toastElem.setAttribute('closing', '') // 이름과 값. 이름만 쓸 것이기 때문에, 값은 할당 안함
        toastElem.addEventListener(
          'animationend',
          () => {
            this.deleteMessage(id)
          },
          { once: true }
        )
      },
      3000,
      id,
      this // 전달 인자
    )
  }

  // 토스트를 추가하는 함수
  addMessage(type: 'success' | 'error' | 'info' | 'warning', message: string) {
    const id = Date.now()
    this.messages.push({
      id,
      type,
      message,
    })
    this.root.render(
      <ToastMessages
        messages={this.messages}
        deleteMessage={this.deleteMessage.bind(this)}
      />
    )
    this.autoDeleteMessage(id)
  }
}

export default new ToastManager()

type ToastMessagesProps = {
  messages: Message[]
  deleteMessage: (id: number) => void
}

function ToastMessages({ messages, deleteMessage }: ToastMessagesProps) {
  function getIcon(type: 'success' | 'error' | 'info' | 'warning') {
    switch (type) {
      case 'success':
        return <FaCheckCircle />
      case 'error':
        return <FaCheckCircle />
      case 'info':
        return <FaInfoCircle />
      case 'warning':
        return <FaExclamationCircle />
    }
  }

  return (
    <>
      <div className={styles.container}>
        {messages.map(({ id, type, message }) => (
          <div
            key={id}
            id={`toast-message-${id}`}
            className={styles.item}
            onClick={() => deleteMessage(id)}
          >
            <div className={`${styles.icon} ${styles[type]}`}>
              {getIcon(type)}
            </div>
            <div>{message}</div>
          </div>
        ))}
      </div>
    </>
  )
}
