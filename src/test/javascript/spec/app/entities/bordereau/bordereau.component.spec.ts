import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { BordereauComponent } from 'app/entities/bordereau/bordereau.component';
import { BordereauService } from 'app/entities/bordereau/bordereau.service';
import { Bordereau } from 'app/shared/model/bordereau.model';

describe('Component Tests', () => {
  describe('Bordereau Management Component', () => {
    let comp: BordereauComponent;
    let fixture: ComponentFixture<BordereauComponent>;
    let service: BordereauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [BordereauComponent],
      })
        .overrideTemplate(BordereauComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BordereauComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BordereauService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bordereau(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bordereaus && comp.bordereaus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
