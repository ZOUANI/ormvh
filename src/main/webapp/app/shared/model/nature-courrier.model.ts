import { Moment } from 'moment';

export interface INatureCourrier {
  id?: number;
  libelle?: string;
  delai?: number;
  relance?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
}

export class NatureCourrier implements INatureCourrier {
  constructor(
    public id?: number,
    public libelle?: string,
    public delai?: number,
    public relance?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string
  ) {}
}
