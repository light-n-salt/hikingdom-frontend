import { AxiosError } from 'axios'

export interface AxiosDataError extends AxiosError {
  data?: any
}

export interface Message {
  message: string
}

export interface InfinitePage {
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}
