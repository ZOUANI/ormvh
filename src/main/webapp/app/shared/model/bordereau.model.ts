import { Moment } from 'moment';
import { ICourrier } from 'app/shared/model/courrier.model';

export interface IBordereau {
  id?: number;
  libelle?: string;
  description?: string;
  nombrePieceJointe?: number;
  dateBordereaux?: Moment;
  courriers?: ICourrier[];
}

export class Bordereau implements IBordereau {
  constructor(
    public id?: number,
    public libelle?: string,
    public description?: string,
    public nombrePieceJointe?: number,
    public dateBordereaux?: Moment,
    public courriers?: ICourrier[]
  ) {}
}
