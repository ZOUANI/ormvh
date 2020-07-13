import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ILeService } from 'app/shared/model/le-service.model';

export interface IEmployee {
  id?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  user?: IUser;
  service?: ILeService;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public user?: IUser,
    public service?: ILeService
  ) {}
}
