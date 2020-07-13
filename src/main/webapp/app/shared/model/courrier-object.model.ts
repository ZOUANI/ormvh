import { Moment } from 'moment';

export interface ICourrierObject {
  id?: number;
  title?: string;
  category?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class CourrierObject implements ICourrierObject {
  constructor(
    public id?: number,
    public title?: string,
    public category?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
