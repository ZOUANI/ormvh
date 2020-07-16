import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICourrier } from 'app/shared/model/courrier.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITask {
  id?: number;
  title?: string;
  description?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  assignedAt?: Moment;
  processedAt?: Moment;
  observation?: string;
  status?: Status;
  createdBy?: string;
  updatedBy?: string;
  delai?: number;
  relance?: number;
  accuse?: boolean;
  reponse?: boolean;
  dateAccuse?: Moment;
  dateReponse?: Moment;
  assigne?: IUser;
  courrier?: ICourrier;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public assignedAt?: Moment,
    public processedAt?: Moment,
    public observation?: string,
    public status?: Status,
    public createdBy?: string,
    public updatedBy?: string,
    public delai?: number,
    public relance?: number,
    public accuse?: boolean,
    public reponse?: boolean,
    public dateAccuse?: Moment,
    public dateReponse?: Moment,
    public assigne?: IUser,
    public courrier?: ICourrier
  ) {
    this.accuse = this.accuse || false;
    this.reponse = this.reponse || false;
  }
}
