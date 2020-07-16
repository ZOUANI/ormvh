import { Moment } from 'moment';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';

export interface IExpeditor {
  id?: number;
  title?: string;
  description?: string;
  nature?: string;
  sexe?: Sexe;
  age?: number;
  nationality?: string;
  adress?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class Expeditor implements IExpeditor {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public nature?: string,
    public sexe?: Sexe,
    public age?: number,
    public nationality?: string,
    public adress?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
