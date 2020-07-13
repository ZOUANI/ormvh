import { Moment } from 'moment';

export interface IExpeditorType {
  id?: number;
  title?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class ExpeditorType implements IExpeditorType {
  constructor(
    public id?: number,
    public title?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
