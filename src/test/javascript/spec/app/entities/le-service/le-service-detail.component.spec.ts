import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { LeServiceDetailComponent } from 'app/entities/le-service/le-service-detail.component';
import { LeService } from 'app/shared/model/le-service.model';

describe('Component Tests', () => {
  describe('LeService Management Detail Component', () => {
    let comp: LeServiceDetailComponent;
    let fixture: ComponentFixture<LeServiceDetailComponent>;
    const route = ({ data: of({ leService: new LeService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [LeServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LeServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeServiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leService on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leService).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
