import { Moment } from 'moment';
import { ICourrier } from 'app/shared/model/courrier.model';

export interface ILeService {
  id?: number;
  title?: string;
  description?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  courriers?: ICourrier[];
}

export class LeService implements ILeService {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public courriers?: ICourrier[]
  ) {}
}
