import { Moment } from 'moment';

export interface IVoie {
  id?: number;
  libelle?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class Voie implements IVoie {
  constructor(
    public id?: number,
    public libelle?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
