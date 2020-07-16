import { Moment } from 'moment';
import { IVoie } from 'app/shared/model/voie.model';
import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { ITask } from 'app/shared/model/task.model';
import { IExpeditor } from 'app/shared/model/expeditor.model';
import { ILeService } from 'app/shared/model/le-service.model';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ISubdivision } from 'app/shared/model/subdivision.model';
import { IBordereau } from 'app/shared/model/bordereau.model';
import { TypeCourrier } from 'app/shared/model/enumerations/type-courrier.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ICourrier {
  id?: number;
  idCourrier?: string;
  subject?: string;
  description?: string;
  typeCourrier?: TypeCourrier;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  delai?: number;
  relance?: number;
  accuse?: boolean;
  reponse?: boolean;
  dateAccuse?: Moment;
  dateReponse?: Moment;
  status?: Status;
  dataContentType?: string;
  data?: any;
  receivedAt?: Moment;
  instruction?: string;
  expediteurDesc?: string;
  sentAt?: Moment;
  destinataireDesc?: string;
  destinataireVille?: string;
  voie?: IVoie;
  natureCourrier?: INatureCourrier;
  linkedTo?: ICourrier;
  tasks?: ITask[];
  expeditor?: IExpeditor;
  destinator?: IExpeditor;
  coordinator?: ILeService;
  emetteur?: ILeService;
  evaluation?: IEvaluation;
  courrierObject?: ICourrierObject;
  expeditorType?: IExpeditorType;
  subdivision?: ISubdivision;
  services?: ILeService[];
  bordereau?: IBordereau;
}

export class Courrier implements ICourrier {
  constructor(
    public id?: number,
    public idCourrier?: string,
    public subject?: string,
    public description?: string,
    public typeCourrier?: TypeCourrier,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public delai?: number,
    public relance?: number,
    public accuse?: boolean,
    public reponse?: boolean,
    public dateAccuse?: Moment,
    public dateReponse?: Moment,
    public status?: Status,
    public dataContentType?: string,
    public data?: any,
    public receivedAt?: Moment,
    public instruction?: string,
    public expediteurDesc?: string,
    public sentAt?: Moment,
    public destinataireDesc?: string,
    public destinataireVille?: string,
    public voie?: IVoie,
    public natureCourrier?: INatureCourrier,
    public linkedTo?: ICourrier,
    public tasks?: ITask[],
    public expeditor?: IExpeditor,
    public destinator?: IExpeditor,
    public coordinator?: ILeService,
    public emetteur?: ILeService,
    public evaluation?: IEvaluation,
    public courrierObject?: ICourrierObject,
    public expeditorType?: IExpeditorType,
    public subdivision?: ISubdivision,
    public services?: ILeService[],
    public bordereau?: IBordereau
  ) {
    this.accuse = this.accuse || false;
    this.reponse = this.reponse || false;
  }
}
