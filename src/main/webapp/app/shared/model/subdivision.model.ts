import { Moment } from 'moment';

export interface ISubdivision {
  id?: number;
  title?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class Subdivision implements ISubdivision {
  constructor(
    public id?: number,
    public title?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
