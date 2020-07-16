import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';
import { CourrierObjectDeleteDialogComponent } from './courrier-object-delete-dialog.component';

@Component({
  selector: 'jhi-courrier-object',
  templateUrl: './courrier-object.component.html',
})
export class CourrierObjectComponent implements OnInit, OnDestroy {
  courrierObjects?: ICourrierObject[];
  eventSubscriber?: Subscription;

  constructor(
    protected courrierObjectService: CourrierObjectService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.courrierObjectService.query().subscribe((res: HttpResponse<ICourrierObject[]>) => (this.courrierObjects = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCourrierObjects();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICourrierObject): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCourrierObjects(): void {
    this.eventSubscriber = this.eventManager.subscribe('courrierObjectListModification', () => this.loadAll());
  }

  delete(courrierObject: ICourrierObject): void {
    const modalRef = this.modalService.open(CourrierObjectDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.courrierObject = courrierObject;
  }
}
