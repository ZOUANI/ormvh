import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITask } from 'app/shared/model/task.model';

type EntityResponseType = HttpResponse<ITask>;
type EntityArrayResponseType = HttpResponse<ITask[]>;

@Injectable({ providedIn: 'root' })
export class TaskService {
  public resourceUrl = SERVER_API_URL + 'api/tasks';

  constructor(protected http: HttpClient) {}

  create(task: ITask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(task);
    return this.http
      .post<ITask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(task: ITask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(task);
    return this.http
      .put<ITask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITask[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(task: ITask): ITask {
    const copy: ITask = Object.assign({}, task, {
      createdAt: task.createdAt && task.createdAt.isValid() ? task.createdAt.toJSON() : undefined,
      updatedAt: task.updatedAt && task.updatedAt.isValid() ? task.updatedAt.toJSON() : undefined,
      assignedAt: task.assignedAt && task.assignedAt.isValid() ? task.assignedAt.toJSON() : undefined,
      processedAt: task.processedAt && task.processedAt.isValid() ? task.processedAt.toJSON() : undefined,
      dateAccuse: task.dateAccuse && task.dateAccuse.isValid() ? task.dateAccuse.format(DATE_FORMAT) : undefined,
      dateReponse: task.dateReponse && task.dateReponse.isValid() ? task.dateReponse.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
      res.body.assignedAt = res.body.assignedAt ? moment(res.body.assignedAt) : undefined;
      res.body.processedAt = res.body.processedAt ? moment(res.body.processedAt) : undefined;
      res.body.dateAccuse = res.body.dateAccuse ? moment(res.body.dateAccuse) : undefined;
      res.body.dateReponse = res.body.dateReponse ? moment(res.body.dateReponse) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((task: ITask) => {
        task.createdAt = task.createdAt ? moment(task.createdAt) : undefined;
        task.updatedAt = task.updatedAt ? moment(task.updatedAt) : undefined;
        task.assignedAt = task.assignedAt ? moment(task.assignedAt) : undefined;
        task.processedAt = task.processedAt ? moment(task.processedAt) : undefined;
        task.dateAccuse = task.dateAccuse ? moment(task.dateAccuse) : undefined;
        task.dateReponse = task.dateReponse ? moment(task.dateReponse) : undefined;
      });
    }
    return res;
  }
}
