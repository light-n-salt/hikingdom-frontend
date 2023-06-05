import { AxiosError } from 'axios'

export interface AxiosDataResponse extends AxiosError {
  data: any
}

export interface AxiosDataError extends AxiosError {
  data: any
}

export interface Option {
  value: string
  label: string
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
