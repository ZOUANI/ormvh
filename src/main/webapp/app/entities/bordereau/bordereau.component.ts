import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';
import { BordereauDeleteDialogComponent } from './bordereau-delete-dialog.component';

@Component({
  selector: 'jhi-bordereau',
  templateUrl: './bordereau.component.html',
})
export class BordereauComponent implements OnInit, OnDestroy {
  bordereaus?: IBordereau[];
  eventSubscriber?: Subscription;

  constructor(protected bordereauService: BordereauService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bordereauService.query().subscribe((res: HttpResponse<IBordereau[]>) => (this.bordereaus = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBordereaus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBordereau): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBordereaus(): void {
    this.eventSubscriber = this.eventManager.subscribe('bordereauListModification', () => this.loadAll());
  }

  delete(bordereau: IBordereau): void {
    const modalRef = this.modalService.open(BordereauDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bordereau = bordereau;
  }
}
