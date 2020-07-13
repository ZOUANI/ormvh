import { Moment } from 'moment';

export interface IEvaluation {
  id?: number;
  title?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class Evaluation implements IEvaluation {
  constructor(
    public id?: number,
    public title?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
