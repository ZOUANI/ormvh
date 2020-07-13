import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { AccountService } from 'app/core';
import { ModelLettreReponseService } from './model-lettre-reponse.service';

@Component({
  selector: 'jhi-model-lettre-reponse',
  templateUrl: './model-lettre-reponse.component.html'
})
export class ModelLettreReponseComponent implements OnInit, OnDestroy {
  modelLettreReponses: IModelLettreReponse[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected modelLettreReponseService: ModelLettreReponseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.modelLettreReponseService
      .query()
      .pipe(
        filter((res: HttpResponse<IModelLettreReponse[]>) => res.ok),
        map((res: HttpResponse<IModelLettreReponse[]>) => res.body)
      )
      .subscribe(
        (res: IModelLettreReponse[]) => {
          this.modelLettreReponses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInModelLettreReponses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IModelLettreReponse) {
    return item.id;
  }

  registerChangeInModelLettreReponses() {
    this.eventSubscriber = this.eventManager.subscribe('modelLettreReponseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
